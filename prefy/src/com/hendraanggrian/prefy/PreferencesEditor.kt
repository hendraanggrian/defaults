package com.hendraanggrian.prefy

/**
 * Interface used for modifying values in a [ReadablePreferences] object.
 * All changes you make in an editor are batched,
 * and not copied back to the original [ReadablePreferences] until you call [save].
 */
interface PreferencesEditor : PreferencesSaver {

    /**
     * Mark in the editor that a preference value should be removed.
     * @param key name of the preference to remove.
     */
    fun remove(key: String)

    /** Mark in the editor to remove *all* values from the preferences. */
    fun clear()

    /**
     * Set a [String] value in the preferences editor.
     * @param key name of the preference to modify.
     * @param value new value for the preference. Passing `null` is equivalent to [remove].
     */
    operator fun set(key: String, value: String?)

    /**
     * Set a [Boolean] value in the preferences editor.
     * @param key name of the preference to modify.
     * @param value new value for the preference. Passing `null` is equivalent to [remove].
     * @throws UnsupportedOperationException if [Boolean] is not a supported type of this preferences.
     */
    operator fun set(key: String, value: Boolean)

    /**
     * Set a [Double] value in the preferences editor.
     * @param key name of the preference to modify.
     * @param value new value for the preference. Passing `null` is equivalent to [remove].
     * @throws UnsupportedOperationException if [Double] is not a supported type of this preferences.
     */
    operator fun set(key: String, value: Double)

    /**
     * Set a [Float] value in the preferences editor.
     * @param key name of the preference to modify.
     * @param value new value for the preference. Passing `null` is equivalent to [remove].
     * @throws UnsupportedOperationException if [Float] is not a supported type of this preferences.
     */
    operator fun set(key: String, value: Float)

    /**
     * Set a [Long] value in the preferences editor.
     * @param key name of the preference to modify.
     * @param value new value for the preference. Passing `null` is equivalent to [remove].
     * @throws UnsupportedOperationException if [Long] is not a supported type of this preferences.
     */
    operator fun set(key: String, value: Long)

    /**
     * Set a [Int] value in the preferences editor.
     * @param key name of the preference to modify.
     * @param value new value for the preference. Passing `null` is equivalent to [remove].
     * @throws UnsupportedOperationException if [Int] is not a supported type of this preferences.
     */
    operator fun set(key: String, value: Int)

    /**
     * Set a [Short] value in the preferences editor.
     * @param key name of the preference to modify.
     * @param value new value for the preference. Passing `null` is equivalent to [remove].
     * @throws UnsupportedOperationException if [Short] is not a supported type of this preferences.
     */
    operator fun set(key: String, value: Short)

    /**
     * Set a [Byte] value in the preferences editor.
     * @param key name of the preference to modify.
     * @param value new value for the preference. Passing `null` is equivalent to [remove].
     * @throws UnsupportedOperationException if [Byte] is not a supported type of this preferences.
     */
    operator fun set(key: String, value: Byte)
}
