package com.hendraanggrian.prefs.compiler

import com.google.auto.common.MoreElements
import com.hendraanggrian.javapoet.classOf
import com.hendraanggrian.prefs.BindPref
import javax.lang.model.element.TypeElement

internal const val TARGET = "target"
internal const val SOURCE = "source"
internal const val EDITOR = "editor"

internal val PREFS_BINDING = "com.hendraanggrian.prefs.internal".classOf("PrefsBinding")
internal val PREFS = "com.hendraanggrian.prefs".classOf("Prefs")
internal val PREFS_EDITOR = PREFS.nestedClass("Editor")

internal val TypeElement.measuredName: String
    @Suppress("UnstableApiUsage") get() {
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
