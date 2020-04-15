package com.hendraanggrian.prefy

/**
 * Interface used for applying changes made in [PreferencesEditor] object.
 * When using [ReadablePreferences.bind] to inject preferences' values to JVM fields,
 * [PreferencesSaver] is returned back to save changes made in JVM fields.
 */
interface PreferencesSaver {

    companion object {
        internal val EMPTY: PreferencesSaver = object : PreferencesSaver {
            override fun save() = Prefy.warn("Saving an empty preferences.")
        }
    }

    /**
     * Commit your preferences changes back from [PreferencesEditor] to the [ReadablePreferences] object it is editing.
     * This atomically performs the requested modifications,
     * replacing whatever is currently in the [ReadablePreferences].
     */
    fun save()
}
