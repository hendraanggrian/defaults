package local

import com.google.auto.common.MoreElements
import com.hendraanggrian.javapoet.classNameOf
import com.squareup.javapoet.ClassName
import javax.lang.model.element.TypeElement

internal const val TARGET = "target"
internal const val SOURCE = "source"
internal const val EDITOR = "editor"

internal val TYPE_LOCAL_BINDING: ClassName =
    classNameOf("local.internal", "LocalBinding")
internal val TYPE_LOCAL: ClassName =
    classNameOf("local", "Local")
internal val TYPE_LOCAL_EDITOR: ClassName =
    classNameOf("local", "LocalEditor")

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
        return "$typeName${BindLocal.SUFFIX}"
    }
