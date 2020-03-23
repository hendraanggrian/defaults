package com.hendraanggrian.prefs

/**
 * Subinterface of [ReadablePrefs] that, in contrast to [WritablePrefs],
 * requires an instance of [Prefs.Editor] to modify preferences.
 * @param E editor of this preferences.
 */
interface EditablePrefs<E : Prefs.Editor> : ReadablePrefs {

    /** Returns an editor to modify preferences. */
    val editor: E

    /**
     * Convenient method to open an editor and apply changes in dsl.
     * @param editAction where editing calls should be.
     */
    fun edit(editAction: E.() -> Unit) {
        val editor = editor
        editor.editAction()
        editor.save()
    }
}
