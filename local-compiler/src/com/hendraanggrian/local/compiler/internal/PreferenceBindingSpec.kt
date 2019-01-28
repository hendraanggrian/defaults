package com.hendraanggrian.local.compiler.internal

import com.google.auto.common.MoreElements.getPackage
import com.google.auto.common.MoreTypes.asTypeElement
import com.hendraanggrian.local.Local
import com.squareup.javapoet.ClassName.get
import com.squareup.javapoet.CodeBlock.of
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec.constructorBuilder
import com.squareup.javapoet.MethodSpec.methodBuilder
import com.squareup.javapoet.TypeSpec.classBuilder
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier.FINAL
import javax.lang.model.element.Modifier.PRIVATE
import javax.lang.model.element.Modifier.PUBLIC
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeKind.NONE
import javax.lang.model.type.TypeKind.VOID

internal class PreferenceBindingSpec(typeElement: TypeElement) {

    companion object {
        private const val TARGET = "target"
        private const val SOURCE = "source"
        private val TYPE_LOCAL_BINDING = get("com.hendraanggrian.local.internal", "LocalBinding")!!
        private val TYPE_LOCAL_SETTINGS = get("com.hendraanggrian.local", "LocalSettings")!!
        private val TYPE_EDITOR = get("com.hendraanggrian.local.LocalSettings", "Editor")!!
    }

    private val mPackageName = getPackage(typeElement).qualifiedName.toString()
    private val mClassName = get(typeElement)
    private val mSuperclass = typeElement.superclass
    private val mClass = classBuilder(typeElement.measuredName)
        .addModifiers(PUBLIC)
        .addField(mClassName, TARGET, PRIVATE, FINAL)

    private val mConstructorMethod = constructorBuilder()
        .addModifiers(PUBLIC)
        .addParameter(mClassName, TARGET)
        .addParameter(TYPE_LOCAL_SETTINGS, SOURCE)
    private val mSaveMethod = methodBuilder("save")
        .addAnnotation(Override::class.java)
        .addModifiers(PUBLIC)
    private val mSaveAsyncMethod = methodBuilder("saveAsync")
        .addAnnotation(Override::class.java)
        .addModifiers(PUBLIC)

    fun superclass(generatedClassNames: Collection<String>): PreferenceBindingSpec {
        var hasSuperclass = false
        if (mSuperclass.kind != NONE && mSuperclass.kind != VOID) {
            val className = asTypeElement(mSuperclass).measuredName
            if (generatedClassNames.contains(className)) {
                mClass.superclass(get(mPackageName, className))
                hasSuperclass = true
            }
        }
        if (!hasSuperclass) {
            mClass.superclass(TYPE_LOCAL_BINDING)
            mConstructorMethod.addStatement("super(\$L)", SOURCE)
        } else {
            mConstructorMethod.addStatement("super(\$L, \$L)", TARGET, SOURCE)
            mSaveMethod.addStatement("super.save()")
            mSaveAsyncMethod.addStatement("super.saveAsync()")
        }
        mConstructorMethod.addStatement("this.\$L = \$L", TARGET, TARGET)
        return this
    }

    fun statement(fieldElements: Iterable<Element>): PreferenceBindingSpec {
        // save
        mSaveMethod.addCode(of("\$T editor = getEditor();\n", TYPE_EDITOR))
        mSaveAsyncMethod.addCode(of("\$T editor = getEditor();\n", TYPE_EDITOR))
        // constructor, save
        fieldElements.forEach { element ->
            val field = element.simpleName.toString()
            val preference = element.getAnnotation(Local::class.java)
            val key = "\"" + (if (!preference!!.value.isEmpty()) preference.value else field) + "\""
            if (PreferenceType.valueOf(element) == null) {
                mConstructorMethod.addStatement(
                    "this.target.\$L = (\$L) target.findPreference(\$L)",
                    field, get(element.asType()), key
                )
            } else {
                mConstructorMethod.addStatement(
                    "this.target.\$L = get(\$L, target.\$L)", field, key, field
                )
                mSaveMethod.addStatement(
                    "editor.set(\$L, target.\$L)", key, field
                )
                mSaveAsyncMethod.addStatement(
                    "editor.set(\$L, target.\$L)", key, field
                )
            }
        }
        mSaveMethod.addStatement("editor.save()")
        mSaveAsyncMethod.addStatement("editor.saveAsync()")
        return this
    }

    fun toJavaFile(): JavaFile = JavaFile
        .builder(mPackageName, mClass.apply {
            addMethod(mConstructorMethod.build())
            addMethod(mSaveMethod.build())
            addMethod(mSaveAsyncMethod.build())
        }.build())
        .addFileComment(
            "Preferencer generated class, " +
                "do not modify! https://github.com/hendraanggrian/preferencer"
        )
        .build()
}