package com.hendraanggrian.prefs

/**
 * Subinterface of [ReadablePrefs] that, in contrast to [WritablePrefs],
 * requires an instance of [Prefs.Editor] to modify preferences.
 */
interface EditablePrefs : ReadablePrefs {

    /** Returns an editor to modify preferences. */
    val editor: Prefs.Editor

    /**
     * Convenient method to open an editor and apply changes in dsl.
     * @param editAction where editing calls should be.
     */
    fun edit(editAction: Prefs.Editor.() -> Unit) {
        val editor = editor
        editor.editAction()
        editor.save()
    }
}
