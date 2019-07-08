package com.hendraanggrian.lokal

import com.google.auto.common.MoreElements
import com.squareup.javapoet.ClassName
import javax.lang.model.element.TypeElement

internal const val TARGET = "target"
internal const val SOURCE = "source"
internal const val EDITOR = "editor"

internal val TYPE_LOKAL_BINDING: ClassName =
    ClassName.get("com.hendraanggrian.lokal.internal", "LokalBinding")
internal val TYPE_LOKAL: ClassName =
    ClassName.get("com.hendraanggrian.lokal", "Lokal")
internal val TYPE_LOKAL_EDITOR: ClassName =
    ClassName.get("com.hendraanggrian.lokal", "Lokal").nestedClass("Editor")

internal val TypeElement.measuredName: String
    get() {
        val enclosings = mutableListOf(simpleName.toString())
        var typeElement = this
        while (typeElement.nestingKind.isNested) {
            typeElement = MoreElements.asType(typeElement.enclosingElement)
            enclosings.add(typeElement.simpleName.toString())
        }
        enclosings.reverse()
        var typeName = enclosings[0]
        for (i in 1 until enclosings.size) typeName += "$${enclosings[i]}"
        return "$typeName${BindLokal.SUFFIX}"
    }