package com.hendraanggrian.prefy;

/**
 * Interface used for modifying values in a {@link ReadablePreferences} object.
 * All changes you make in an editor are batched,
 * and not copied back to the original {@link ReadablePreferences} until you call {@link PreferencesSaver#save}.
 */
public interface PreferencesEditor extends PreferencesSaver {

    /**
     * Mark in the editor that a preference value should be removed.
     *
     * @param key name of the preference to remove.
     */
    void remove(String key);

    /**
     * Mark in the editor to remove *all* values from the preferences.
     */
    void clear();

    /**
     * Set a {@link String} value in the preferences editor.
     *
     * @param key   name of the preference to modify.
     * @param value new value for the preference.
     *              Passing {@code null} is equivalent to {@link PreferencesEditor#remove}.
     */
    void set(String key, String value);

    /**
     * Set a {@link Boolean} value in the preferences editor.
     *
     * @param key   name of the preference to modify.
     * @param value new value for the preference.
     *              Passing {@code null} is equivalent to {@link PreferencesEditor#remove}.
     * @throws UnsupportedOperationException if {@link Boolean} is not a supported type of this preferences.
     */
    void set(String key, boolean value);

    /**
     * Set a {@link Double} value in the preferences editor.
     *
     * @param key   name of the preference to modify.
     * @param value new value for the preference.
     *              Passing {@code null} is equivalent to {@link PreferencesEditor#remove}.
     * @throws UnsupportedOperationException if {@link Double} is not a supported type of this preferences.
     */
    void set(String key, double value);

    /**
     * Set a {@link Float} value in the preferences editor.
     *
     * @param key   name of the preference to modify.
     * @param value new value for the preference.
     *              Passing {@code null} is equivalent to {@link PreferencesEditor#remove}.
     * @throws UnsupportedOperationException if {@link Float} is not a supported type of this preferences.
     */
    void set(String key, float value);

    /**
     * Set a {@link Long} value in the preferences editor.
     *
     * @param key   name of the preference to modify.
     * @param value new value for the preference.
     *              Passing {@code null} is equivalent to {@link PreferencesEditor#remove}.
     * @throws UnsupportedOperationException if {@link Long} is not a supported type of this preferences.
     */
    void set(String key, long value);

    /**
     * Set a {@link Integer} value in the preferences editor.
     *
     * @param key   name of the preference to modify.
     * @param value new value for the preference.
     *              Passing {@code null} is equivalent to {@link PreferencesEditor#remove}.
     * @throws UnsupportedOperationException if {@link Integer} is not a supported type of this preferences.
     */
    void set(String key, int value);

    /**
     * Set a {@link Short} value in the preferences editor.
     *
     * @param key   name of the preference to modify.
     * @param value new value for the preference.
     *              Passing {@code null} is equivalent to {@link PreferencesEditor#remove}.
     * @throws UnsupportedOperationException if {@link Short} is not a supported type of this preferences.
     */
    void set(String key, short value);

    /**
     * Set a {@link Byte} value in the preferences editor.
     *
     * @param key   name of the preference to modify.
     * @param value new value for the preference.
     *              Passing {@code null} is equivalent to {@link PreferencesEditor#remove}.
     * @throws UnsupportedOperationException if {@link Byte} is not a supported type of this preferences.
     */
    void set(String key, byte value);
}
