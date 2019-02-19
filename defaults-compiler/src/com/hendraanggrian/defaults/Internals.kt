package com.hendraanggrian.defaults

import com.google.auto.common.MoreElements
import com.squareup.javapoet.ClassName
import java.util.Collections.reverse
import javax.lang.model.element.TypeElement

internal const val TARGET = "target"
internal const val SOURCE = "source"

internal val TYPE_DEFAULTS_BINDING: ClassName =
    ClassName.get("com.hendraanggrian.defaults.internal", "DefaultsBinding")
internal val TYPE_READABLE_DEFAULTS: ClassName =
    ClassName.get("com.hendraanggrian.defaults", "ReadableDefaults")
internal val TYPE_DEFAULTS_EDITOR: ClassName =
    ClassName.get("com.hendraanggrian.defaults", "DefaultsEditor")

internal val TypeElement.measuredName: String
    get() {
        val enclosings = mutableListOf(simpleName.toString())
        var typeElement = this
        while (typeElement.nestingKind.isNested) {
            typeElement = MoreElements.asType(typeElement.enclosingElement)
            enclosings.add(typeElement.simpleName.toString())
        }
        reverse(enclosings)
        var typeName = enclosings[0]
        for (i in 1 until enclosings.size) typeName += "$${enclosings[i]}"
        return "$typeName${BindDefault.SUFFIX}"
    }