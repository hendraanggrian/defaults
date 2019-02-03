package com.hendraanggrian.defaults

/** Represents a local settings that can directly write values without editor being created. */
interface WritableDefaults : ReadableDefaults, Defaults.Editor {

    /**
     * Convenient method to quickly open an editor and apply changes in dsl.
     *
     * @param edit receiver is [Defaults] for access to settings' contents, next param is [Writer]
     *        for custom editing.
     */
    infix operator fun invoke(edit: (WritableDefaults.() -> Unit)): WritableDefaults = also {
        edit(it)
        it.saveAsync()
    }
}