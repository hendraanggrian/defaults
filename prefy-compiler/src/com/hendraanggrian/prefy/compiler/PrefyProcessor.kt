package com.hendraanggrian.prefy.compiler

import com.google.auto.common.MoreElements
import com.google.auto.common.MoreTypes
import com.google.common.collect.LinkedHashMultimap
import com.hendraanggrian.javapoet.asClassName
import com.hendraanggrian.javapoet.buildJavaFile
import com.hendraanggrian.javapoet.classOf
import com.hendraanggrian.prefy.BindPreference
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Filer
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeKind

/**
 * Annotation processor responsible for generating sources necessary for binding preferences.
 * The binding API is available at core repository.
 */
class PrefyProcessor : AbstractProcessor() {
    private lateinit var filer: Filer

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latestSupported()

    override fun getSupportedAnnotationTypes(): Set<String> = setOf(BindPreference::class.java.canonicalName)

    @Synchronized override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        filer = processingEnv.filer
    }

    @Suppress("UnstableApiUsage")
    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
        // preparing elements
        val multimap = LinkedHashMultimap.create<TypeElement, Element>()
        val measuredClassNames = mutableSetOf<String>()
        roundEnv.getElementsAnnotatedWith(BindPreference::class.java).forEach { element ->
            val typeElement = MoreElements.asType(element.enclosingElement)
            multimap.put(typeElement, element)
            measuredClassNames += typeElement.measuredName
        }

        // generate classes
        multimap.keySet().map { it to multimap[it] }.forEach { (typeElement, elements) ->
            val className = typeElement.asClassName()
            val packageName = typeElement.measuredPackageName
            buildJavaFile(packageName) {
                comment = "Prefy generated class, do not modify!"
                addClass(typeElement.measuredName) {
                    var hasSupercls = false
                    val supercls = typeElement.superclass
                    if (supercls.kind != TypeKind.NONE && supercls.kind != TypeKind.VOID) {
                        val measuredClassName = MoreTypes.asTypeElement(supercls).measuredName
                        if (measuredClassName in measuredClassNames) {
                            superclass = packageName.classOf(measuredClassName)
                            hasSupercls = true
                        }
                    }
                    if (!hasSupercls) {
                        superclass = PREFERENCES_BINDING
                    }
                    addModifiers(Modifier.PUBLIC)
                    fields.add(className, TARGET, Modifier.PRIVATE, Modifier.FINAL)
                    methods {
                        addConstructor {
                            addModifiers(Modifier.PUBLIC)
                            parameters {
                                add(READABLE_PREFERENCES, SOURCE, Modifier.FINAL)
                                add(className, TARGET, Modifier.FINAL)
                            }
                            when {
                                !hasSupercls -> appendln("super(%L)", SOURCE)
                                else -> appendln("super(%L, %L)", SOURCE, TARGET)
                            }
                            appendln("this.%L = %L", TARGET, TARGET)
                            elements.forEachValue { field, key ->
                                appendln("this.$TARGET.%L = get(%L, $TARGET.%L)", field, key, field)
                            }
                        }
                        "save" {
                            addModifiers(Modifier.PUBLIC)
                            this.annotations.add<Override>()
                            if (hasSupercls) {
                                appendln("super.save()")
                            }
                            appendln("final %T $EDITOR = getEditor()", PREFERENCES_EDITOR)
                            elements.forEachValue { field, key ->
                                appendln("$EDITOR.set(%L, $TARGET.%L)", key, field)
                            }
                            appendln("$EDITOR.save()")
                        }
                    }
                }
            }.writeTo(filer)
        }
        return false
    }

    private inline fun Iterable<Element>.forEachValue(action: (field: String, key: String) -> Unit) =
        forEach { element ->
            val field = element.simpleName.toString()
            val preference = element.getAnnotation(BindPreference::class.java)
            val key = "\"${if (preference!!.value.isNotEmpty()) preference.value else field}\""
            action(field, key)
        }
}
