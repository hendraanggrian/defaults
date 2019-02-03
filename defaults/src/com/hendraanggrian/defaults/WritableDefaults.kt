package com.hendraanggrian.defaults

/** A set of writable local settings, no instance of [DefaultsEditor] is created. */
interface WritableDefaults : ReadableDefaults, DefaultsEditor {

    /** Convenient method to quickly open an editor and apply changes in dsl. */
    operator fun invoke(edit: (WritableDefaults.() -> Unit)): WritableDefaults = also {
        edit(it)
        it.saveAsync()
    }
}