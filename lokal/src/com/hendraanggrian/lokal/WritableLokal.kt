package com.hendraanggrian.lokal

/** A set of writable local settings, no instance of [LokalEditor] is created. */
interface WritableLokal : ReadableLokal, LokalEditor {

    /** Convenient method to quickly open an editor and apply changes in dsl. */
    operator fun invoke(edit: (WritableLokal.() -> Unit)): WritableLokal = also {
        edit(it)
        it.saveAsync()
    }
}