package com.hendraanggrian.prefy

/**
 * Subinterface of [ReadablePreferences] that, in contrast to [WritablePreferences],
 * requires an instance of [PreferencesEditor] to modify preferences.
 * @param E editor of this preferences.
 */
interface EditablePreferences<E : PreferencesEditor> : ReadablePreferences {

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
