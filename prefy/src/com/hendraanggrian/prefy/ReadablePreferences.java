package com.hendraanggrian.prefy;

/**
 * Interface for accessing and modifying preference data on any target platform (JVM, Android, etc.).
 *
 * @see WritablePreferences
 * @see EditablePreferences
 */
public interface ReadablePreferences {

    /**
     * Checks whether the preferences contains a preference.
     *
     * @param key name of the preference to check.
     * @return true if the preference exists in the preferences, otherwise false.
     */
    boolean contains(String key);

    /**
     * Retrieve a {@link String} value from the preferences.
     *
     * @param key name of the preference to retrieve.
     * @return preference value if it exists, or null.
     */
    String get(String key);

    /**
     * Retrieve a {@link String} value from the preferences, providing default value as a fallback.
     *
     * @param key          name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     */
    String getOrDefault(String key, String defaultValue);

    /**
     * Retrieve a {@link Boolean} value from the preferences.
     *
     * @param key name of the preference to retrieve.
     * @return preference value if it exists, or null.
     * @throws UnsupportedOperationException if {@link Boolean} is not a supported type of this preferences.
     */
    Boolean getBoolean(String key);

    /**
     * Retrieve a {@link Boolean} value from the preferences, providing default value as a fallback.
     *
     * @param key          name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if {@link Boolean} is not a supported type of this preferences.
     */
    boolean getBooleanOrDefault(String key, boolean defaultValue);

    /**
     * Retrieve a {@link Double} value from the preferences.
     *
     * @param key name of the preference to retrieve.
     * @return preference value if it exists, or null.
     * @throws UnsupportedOperationException if {@link Double} is not a supported type of this preferences.
     */
    Double getDouble(String key);

    /**
     * Retrieve a {@link Double} value from the preferences, providing default value as a fallback.
     *
     * @param key          name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if {@link Double} is not a supported type of this preferences.
     */
    double getDoubleOrDefault(String key, double defaultValue);

    /**
     * Retrieve a {@link Float} value from the preferences.
     *
     * @param key name of the preference to retrieve.
     * @return preference value if it exists, or null.
     * @throws UnsupportedOperationException if {@link Float} is not a supported type of this preferences.
     */
    Float getFloat(String key);

    /**
     * Retrieve a {@link Float} value from the preferences, providing default value as a fallback.
     *
     * @param key          name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if {@link Float} is not a supported type of this preferences.
     */
    float getFloatOrDefault(String key, float defaultValue);

    /**
     * Retrieve a {@link Long} value from the preferences.
     *
     * @param key name of the preference to retrieve.
     * @return preference value if it exists, or null.
     * @throws UnsupportedOperationException if {@link Long} is not a supported type of this preferences.
     */
    Long getLong(String key);

    /**
     * Retrieve a {@link Long} value from the preferences, providing default value as a fallback.
     *
     * @param key          name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if {@link Long} is not a supported type of this preferences.
     */
    long getLongOrDefault(String key, long defaultValue);

    /**
     * Retrieve an {@link Integer} value from the preferences.
     *
     * @param key name of the preference to retrieve.
     * @return preference value if it exists, or null.
     * @throws UnsupportedOperationException if {@link Integer} is not a supported type of this preferences.
     */
    Integer getInt(String key);

    /**
     * Retrieve an {@link Integer} value from the preferences, providing default value as a fallback.
     *
     * @param key          name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if {@link Integer} is not a supported type of this preferences.
     */
    int getIntOrDefault(String key, int defaultValue);

    /**
     * Retrieve a {@link Short} value from the preferences.
     *
     * @param key name of the preference to retrieve.
     * @return preference value if it exists, or null.
     * @throws UnsupportedOperationException if {@link Short} is not a supported type of this preferences.
     */
    Short getShort(String key);

    /**
     * Retrieve a {@link Short} value from the preferences, providing default value as a fallback.
     *
     * @param key          name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if {@link Short} is not a supported type of this preferences.
     */
    short getShortOrDefault(String key, short defaultValue);

    /**
     * Retrieve a {@link Byte} value from the preferences.
     *
     * @param key name of the preference to retrieve.
     * @return preference value if it exists, or null.
     * @throws UnsupportedOperationException if {@link Byte} is not a supported type of this preferences.
     */
    Byte getByte(String key);

    /**
     * Retrieve a {@link Byte} value from the preferences, providing default value as a fallback.
     *
     * @param key          name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if {@link Byte} is not a supported type of this preferences.
     */
    byte getByteOrDefault(String key, byte defaultValue);
}
