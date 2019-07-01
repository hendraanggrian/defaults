package com.hendraanggrian.defaults

import com.google.auto.common.MoreElements
import com.google.auto.common.MoreTypes
import com.google.common.collect.LinkedHashMultimap
import com.hendraanggrian.javapoet.buildJavaFile
import com.hendraanggrian.javapoet.dsl.MethodContainerScope
import com.squareup.javapoet.ClassName
import java.io.IOException
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Filer
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeKind

class DefaultsProcessor : AbstractProcessor() {

    private lateinit var _filer: Filer

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latestSupported()

    override fun getSupportedAnnotationTypes(): Set<String> =
        setOf(BindDefault::class.java.canonicalName)

    @Synchronized
    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        _filer = processingEnv.filer
    }

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
        // preparing elements
        val multimap = LinkedHashMultimap.create<TypeElement, Element>()
        val measuredClassNames = mutableSetOf<String>()
        roundEnv.getElementsAnnotatedWith(BindDefault::class.java).forEach { element ->
            val typeElement = MoreElements.asType(element.enclosingElement)
            multimap.put(typeElement, element)
            measuredClassNames.add(typeElement.measuredName)
        }

        // generate classes
        multimap.keySet().map { it to multimap[it] }.forEach { (typeElement, elements) ->
            val className = ClassName.get(typeElement)
            val packageName = MoreElements.getPackage(typeElement).qualifiedName.toString()
            val javaFile = buildJavaFile(packageName) {
                comment = "Defaults generated class, do not modify."
                var hasSuperclass = false
                addClass(typeElement.measuredName) {
                    val superclass = typeElement.superclass
                    if (superclass.kind != TypeKind.NONE && superclass.kind != TypeKind.VOID) {
                        val measuredClassName = MoreTypes.asTypeElement(superclass).measuredName
                        if (measuredClassName in measuredClassNames) {
                            superClass = ClassName.get(packageName, measuredClassName)
                            hasSuperclass = true
                        }
                    }
                    if (!hasSuperclass) {
                        superClass = TYPE_DEFAULTS_BINDING
                    }
                    modifiers = public
                    fields {
                        TARGET(className) {
                            modifiers = private + final
                        }
                    }
                    methods {
                        addConstructor {
                            modifiers = public
                            parameters {
                                add(className, TARGET)
                                add(TYPE_READABLE_DEFAULTS, SOURCE)
                            }
                            statements {
                                when {
                                    !hasSuperclass -> add("super(\$L)", SOURCE)
                                    else -> add("super(\$L, \$L)", TARGET, SOURCE)
                                }
                                add("this.\$L = \$L", TARGET, TARGET)
                                elements.forEachValue { field, key ->
                                    add(
                                        "this.$TARGET.\$L = get(\$L, $TARGET.\$L)",
                                        field,
                                        key,
                                        field
                                    )
                                }
                            }
                        }
                        saveMethod("save", hasSuperclass, elements)
                        saveMethod("saveAsync", hasSuperclass, elements)
                    }
                }
            }
            try {
                javaFile.writeTo(_filer)
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }
        return false
    }

    private fun MethodContainerScope.saveMethod(
        name: String,
        hasSuperclass: Boolean,
        elements: Iterable<Element>
    ) {
        name {
            modifiers = public
            annotations.add<Override>()
            statements {
                if (hasSuperclass) {
                    add("super.$name()")
                }
                add("final \$T editor = getEditor()", TYPE_DEFAULTS_EDITOR)
                elements.forEachValue { field, key ->
                    add("editor.set(\$L, $TARGET.\$L)", key, field)
                }
                add("editor.$name()")
            }
        }
    }

    private inline fun Iterable<Element>.forEachValue(action: (field: String, key: String) -> Unit) =
        forEach { element ->
            val field = element.simpleName.toString()
            val preference = element.getAnnotation(BindDefault::class.java)
            val key = "\"${if (preference!!.value.isNotEmpty()) preference.value else field}\""
            action(field, key)
        }
}