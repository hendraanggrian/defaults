package local

import com.google.auto.common.MoreElements
import com.squareup.javapoet.ClassName
import javax.lang.model.element.TypeElement

internal const val TARGET = "target"
internal const val SOURCE = "source"
internal const val WRITER = "writer"

internal val TYPE_LOCAL_BINDING: ClassName =
    ClassName.get("local.internal", "LocalBinding")
internal val TYPE_READABLE_LOCAL: ClassName =
    ClassName.get("local", "ReadableLocal")
internal val TYPE_LOCAL_WRITER: ClassName =
    ClassName.get("local", "LocalWriter")

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
        return "$typeName${BindLocal.SUFFIX}"
    }
