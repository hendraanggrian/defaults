package com.hendraanggrian.local.compiler.internal

import com.google.auto.common.MoreElements.asType
import com.hendraanggrian.local.BindLocal
import java.util.Collections.reverse
import javax.lang.model.element.TypeElement

internal val TypeElement.measuredName: String
    get() {
        val enclosings = mutableListOf(simpleName.toString())
        var typeElement = this
        while (typeElement.nestingKind.isNested) {
            typeElement = asType(typeElement.enclosingElement)
            enclosings.add(typeElement.simpleName.toString())
        }
        reverse(enclosings)
        var typeName = enclosings[0]
        for (i in 1 until enclosings.size) typeName += "$${enclosings[i]}"
        return "$typeName${BindLocal.SUFFIX}"
    }