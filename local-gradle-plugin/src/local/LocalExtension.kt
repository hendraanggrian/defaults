package local

import org.gradle.api.Action
import org.gradle.kotlin.dsl.invoke

open class LocalExtension {

    private val impl = LocalBuilderImpl()

    var packageName: String = ""

    fun configure(s: String, action: Action<LocalBuilder>) {
        s.trim()
        action(impl)
    }

    operator fun String.invoke(action: LocalBuilder.() -> Unit) =
        configure(this, action)

    private class LocalBuilderImpl : LocalBuilder()
}
