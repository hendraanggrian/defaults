package com.hendraanggrian.prefy.compiler

import com.google.auto.common.MoreElements
import com.google.auto.common.MoreTypes
import com.google.common.collect.LinkedHashMultimap
import com.hendraanggrian.javapoet.FINAL
import com.hendraanggrian.javapoet.PRIVATE
import com.hendraanggrian.javapoet.PUBLIC
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
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeKind

/**
 * Annotation processor responsible for generating sources necessary for binding preferences.
 * The binding API is available at core repository.
 */
class PrefyProcessor : AbstractProcessor() {
    private lateinit var filer: Filer

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latestSupported()

    override fun getSupportedAnnotationTypes(): Set<String> =
        setOf(BindPreference::class.java.canonicalName)

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
                    addModifiers(PUBLIC)
                    fields.add(className, TARGET, PRIVATE, FINAL)
                    methods {
                        addConstructor {
                            addModifiers(PUBLIC)
                            parameters {
                                add(READABLE_PREFERENCES, SOURCE, FINAL)
                                add(className, TARGET, FINAL)
                            }
                            when {
                                !hasSupercls -> appendLine("super(%L)", SOURCE)
                                else -> appendLine("super(%L, %L)", SOURCE, TARGET)
                            }
                            appendLine("this.%L = %L", TARGET, TARGET)
                            elements.forEachValue { field, key ->
                                appendLine(
                                    "this.$TARGET.%L = get(%L, $TARGET.%L)",
                                    field, key, field
                                )
                            }
                        }
                        "save" {
                            addModifiers(PUBLIC)
                            this.annotations.add<Override>()
                            if (hasSupercls) {
                                appendLine("super.save()")
                            }
                            appendLine("final %T $EDITOR = getEditor()", PREFERENCES_EDITOR)
                            elements.forEachValue { field, key ->
                                appendLine("$EDITOR.set(%L, $TARGET.%L)", key, field)
                            }
                            appendLine("$EDITOR.save()")
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
