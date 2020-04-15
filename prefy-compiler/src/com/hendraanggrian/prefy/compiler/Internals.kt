package com.hendraanggrian.prefy.compiler

import com.google.auto.common.MoreElements
import com.hendraanggrian.javapoet.classOf
import javax.lang.model.element.TypeElement

internal const val TARGET = "target"
internal const val SOURCE = "source"
internal const val EDITOR = "editor"

internal val PREFERENCES_BINDING = "com.hendraanggrian.prefy.internal".classOf("PreferencesBinding")
internal val READABLE_PREFERENCES = "com.hendraanggrian.prefy".classOf("ReadablePreferences")
internal val PREFERENCES_EDITOR = "com.hendraanggrian.prefy".classOf("PreferencesEditor")

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
        return "${typeName}_PreferencesBinding"
    }
