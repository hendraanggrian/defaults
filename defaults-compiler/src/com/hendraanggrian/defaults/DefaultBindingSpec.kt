package com.hendraanggrian.defaults

import com.google.auto.common.MoreElements.getPackage
import com.google.auto.common.MoreTypes.asTypeElement
import com.squareup.javapoet.ClassName.get
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.MethodSpec.constructorBuilder
import com.squareup.javapoet.TypeSpec.classBuilder
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier.FINAL
import javax.lang.model.element.Modifier.PRIVATE
import javax.lang.model.element.Modifier.PUBLIC
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeKind.NONE
import javax.lang.model.type.TypeKind.VOID

internal class DefaultBindingSpec(typeElement: TypeElement) {

    companion object {
        private const val TARGET = "target"
        private const val SOURCE = "source"
        private val TYPE_DEFAULTS_BINDING =
            get("com.hendraanggrian.defaults.internal", "DefaultsBinding")!!
        private val TYPE_DEFAULTS = get("com.hendraanggrian.defaults", "Defaults")!!
        private val TYPE_DEFAULTS_EDITOR = get("com.hendraanggrian.defaults", "DefaultsEditor")!!
    }

    private val packageName = getPackage(typeElement).qualifiedName.toString()
    private val className = get(typeElement)
    private val superclass = typeElement.superclass
    private val klass = classBuilder(typeElement.measuredName)
        .addModifiers(PUBLIC)
        .addField(
            className,
            TARGET, PRIVATE, FINAL
        )

    private val constructorMethod = constructorBuilder()
        .addModifiers(PUBLIC)
        .addParameter(
            className,
            TARGET
        )
        .addParameter(
            TYPE_DEFAULTS,
            SOURCE
        )
    private val saveMethod = MethodSpec.methodBuilder("save")
        .addAnnotation(Override::class.java)
        .addModifiers(PUBLIC)
    private val saveAsyncMethod = MethodSpec.methodBuilder("saveAsync")
        .addAnnotation(Override::class.java)
        .addModifiers(PUBLIC)

    fun superclass(generatedClassNames: Collection<String>): DefaultBindingSpec {
        var hasSuperclass = false
        if (superclass.kind != NONE && superclass.kind != VOID) {
            val className = asTypeElement(superclass).measuredName
            if (generatedClassNames.contains(className)) {
                klass.superclass(get(packageName, className))
                hasSuperclass = true
            }
        }
        if (!hasSuperclass) {
            klass.superclass(TYPE_DEFAULTS_BINDING)
            constructorMethod.addStatement(
                "super(\$L)",
                SOURCE
            )
        } else {
            constructorMethod.addStatement(
                "super(\$L, \$L)",
                TARGET,
                SOURCE
            )
            saveMethod.addStatement("super.save()")
            saveAsyncMethod.addStatement("super.saveAsync()")
        }
        constructorMethod.addStatement(
            "this.\$L = \$L",
            TARGET,
            TARGET
        )
        return this
    }

    fun statement(fieldElements: Iterable<Element>): DefaultBindingSpec {
        // save
        saveMethod.addCode(CodeBlock.of("\$T editor = getEditor();\n", TYPE_DEFAULTS_EDITOR))
        saveAsyncMethod.addCode(CodeBlock.of("\$T editor = getEditor();\n", TYPE_DEFAULTS_EDITOR))
        // constructor, save
        fieldElements.forEach { element ->
            val field = element.simpleName.toString()
            val preference = element.getAnnotation(BindDefault::class.java)
            val key = "\"" + (if (!preference!!.value.isEmpty()) preference.value else field) + "\""
            constructorMethod.addStatement(
                "this.target.\$L = get(\$L, target.\$L)", field, key, field
            )
            saveMethod.addStatement(
                "editor.set(\$L, target.\$L)", key, field
            )
            saveAsyncMethod.addStatement(
                "editor.set(\$L, target.\$L)", key, field
            )
        }
        saveMethod.addStatement("editor.save()")
        saveAsyncMethod.addStatement("editor.saveAsync()")
        return this
    }

    fun toJavaFile(): JavaFile = JavaFile
        .builder(packageName, klass.apply {
            addMethod(constructorMethod.build())
            addMethod(saveMethod.build())
            addMethod(saveAsyncMethod.build())
        }.build())
        .addFileComment(
            "Defaults generated class, " +
                "do not modify! https://github.com/hendraanggrian/defaults"
        )
        .build()
}