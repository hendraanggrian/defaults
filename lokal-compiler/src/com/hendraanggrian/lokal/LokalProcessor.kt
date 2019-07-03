package com.hendraanggrian.lokal

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
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeKind

class LokalProcessor : AbstractProcessor() {

    private lateinit var _filer: Filer

    override fun getSupportedSourceVersion(): SourceVersion =
        SourceVersion.latestSupported()

    override fun getSupportedAnnotationTypes(): Set<String> =
        setOf(BindLokal::class.java.canonicalName)

    @Synchronized
    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        _filer = processingEnv.filer
    }

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
        // preparing elements
        val multimap = LinkedHashMultimap.create<TypeElement, Element>()
        val measuredClassNames = mutableSetOf<String>()
        roundEnv.getElementsAnnotatedWith(BindLokal::class.java).forEach { element ->
            val typeElement = MoreElements.asType(element.enclosingElement)
            multimap.put(typeElement, element)
            measuredClassNames.add(typeElement.measuredName)
        }

        // generate classes
        multimap.keySet().map { it to multimap[it] }.forEach { (typeElement, elements) ->
            val className = ClassName.get(typeElement)
            val packageName = MoreElements.getPackage(typeElement).qualifiedName.toString()
            val javaFile = buildJavaFile(packageName) {
                comment = "Lokal generated class, do not modify."
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
                        superClass = TYPE_LOKAL_BINDING
                    }
                    addModifiers(Modifier.PUBLIC)
                    fields {
                        TARGET(className) {
                            addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        }
                    }
                    methods {
                        addConstructor {
                            addModifiers(Modifier.PUBLIC)
                            parameters {
                                add(className, TARGET)
                                add(TYPE_READABLE_LOKAL, SOURCE)
                            }
                            codes {
                                when {
                                    !hasSuperclass -> addStatement("super(\$L)", SOURCE)
                                    else -> addStatement("super(\$L, \$L)", TARGET, SOURCE)
                                }
                                addStatement("this.\$L = \$L", TARGET, TARGET)
                                elements.forEachValue { field, key ->
                                    addStatement(
                                        "this.$TARGET.\$L = get(\$L, $TARGET.\$L)",
                                        field, key, field
                                    )
                                }
                            }
                        }
                        addSaveMethod("save", hasSuperclass, elements)
                        addSaveMethod("saveAsync", hasSuperclass, elements)
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

    private fun MethodContainerScope.addSaveMethod(
        name: String,
        hasSuperclass: Boolean,
        elements: Iterable<Element>
    ) {
        name {
            addModifiers(Modifier.PUBLIC)
            annotations.add<Override>()
            codes {
                if (hasSuperclass) {
                    addStatement("super.$name()")
                }
                addStatement("final \$T editor = getEditor()", TYPE_LOKAL_EDITOR)
                elements.forEachValue { field, key ->
                    addStatement("editor.set(\$L, $TARGET.\$L)", key, field)
                }
                addStatement("editor.$name()")
            }
        }
    }

    private inline fun Iterable<Element>.forEachValue(action: (field: String, key: String) -> Unit) =
        forEach { element ->
            val field = element.simpleName.toString()
            val preference = element.getAnnotation(BindLokal::class.java)
            val key = "\"${if (preference!!.value.isNotEmpty()) preference.value else field}\""
            action(field, key)
        }
}