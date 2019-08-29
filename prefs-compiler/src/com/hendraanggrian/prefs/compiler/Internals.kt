package com.hendraanggrian.prefs.compiler

import com.google.auto.common.MoreElements
import com.hendraanggrian.javapoet.classNameOf
import com.hendraanggrian.prefs.BindPref
import com.squareup.javapoet.ClassName
import javax.lang.model.element.TypeElement

internal const val TARGET = "target"
internal const val SOURCE = "source"
internal const val EDITOR = "editor"

internal val TYPE_PREFS_BINDING: ClassName =
    classNameOf("com.hendraanggrian.prefs.internal", "PrefsBinding")
internal val TYPE_PREFS: ClassName =
    classNameOf("com.hendraanggrian.prefs", "Prefs")
internal val TYPE_PREFS_EDITOR: ClassName =
    classNameOf("com.hendraanggrian.prefs", "PrefsEditor")

internal val TypeElement.measuredName: String
    get() {
        val enclosings = mutableListOf(simpleName.toString())
        var typeElement = this
        while (typeElement.nestingKind.isNested) {
            typeElement = MoreElements.asType(typeElement.enclosingElement)
            enclosings.add(typeElement.simpleName.toString())
        }
        enclosings.reverse()
        var typeName = enclosings.first()
        for (i in 1 until enclosings.size) {
            typeName += "$${enclosings[i]}"
        }
        return "$typeName${BindPref.SUFFIX}"
    }
