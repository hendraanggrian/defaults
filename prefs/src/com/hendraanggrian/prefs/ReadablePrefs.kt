package com.hendraanggrian.prefs

/**
 * Interface for accessing and modifying preference data on any target platform (JVM, Android, etc.).
 *
 * @see WritablePrefs
 * @see EditablePrefs
 */
interface ReadablePrefs {

    /**
     * Checks whether the preferences contains a preference.
     * @param key name of the preference to check.
     * @return true if the preference exists in the preferences, otherwise false.
     */
    operator fun contains(key: String): Boolean

    /**
     * Retrieve a [String] value from the preferences.
     * @param key name of the preference to retrieve.
     * @return preference value if it exists, or null.
     */
    operator fun get(key: String): String?

    /**
     * Retrieve a [String] value from the preferences, providing default value as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     */
    fun getOrDefault(key: String, defaultValue: String): String =
        findValue(key, defaultValue) { get(it) }

    /**
     * Retrieve a [String] value from the preferences, calling default value getter as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     */
    fun getOrElse(key: String, defaultValue: () -> String): String =
        getOrDefault(key, defaultValue())

    /**
     * Retrieve a [Boolean] value from the preferences.
     * @param key name of the preference to retrieve.
     * @return preference value if it exists, or null.
     * @throws UnsupportedOperationException if [Boolean] is not a supported type of this preferences.
     */
    fun getBoolean(key: String): Boolean?

    /**
     * Retrieve a [Boolean] value from the preferences, providing default value as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if [Boolean] is not a supported type of this preferences.
     */
    fun getBooleanOrDefault(key: String, defaultValue: Boolean): Boolean =
        findValue(key, defaultValue) { getBoolean(it) }

    /**
     * Retrieve a [Boolean] value from the preferences, calling default value getter as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if [Boolean] is not a supported type of this preferences.
     */
    fun getBooleanOrElse(key: String, defaultValue: () -> Boolean): Boolean =
        getBooleanOrDefault(key, defaultValue())

    /**
     * Retrieve a [Double] value from the preferences.
     * @param key name of the preference to retrieve.
     * @return preference value if it exists, or null.
     * @throws UnsupportedOperationException if [Double] is not a supported type of this preferences.
     */
    fun getDouble(key: String): Double?

    /**
     * Retrieve a [Double] value from the preferences, providing default value as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if [Double] is not a supported type of this preferences.
     */
    fun getDoubleOrDefault(key: String, defaultValue: Double): Double =
        findValue(key, defaultValue) { getDouble(it) }

    /**
     * Retrieve a [Double] value from the preferences, calling default value getter as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if [Double] is not a supported type of this preferences.
     */
    fun getDoubleOrElse(key: String, defaultValue: () -> Double): Double =
        getDoubleOrDefault(key, defaultValue())

    /**
     * Retrieve a [Float] value from the preferences.
     * @param key name of the preference to retrieve.
     * @return preference value if it exists, or null.
     * @throws UnsupportedOperationException if [Float] is not a supported type of this preferences.
     */
    fun getFloat(key: String): Float?

    /**
     * Retrieve a [Float] value from the preferences, providing default value as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if [Float] is not a supported type of this preferences.
     */
    fun getFloatOrDefault(key: String, defaultValue: Float): Float =
        findValue(key, defaultValue) { getFloat(it) }

    /**
     * Retrieve a [Float] value from the preferences, calling default value getter as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if [Float] is not a supported type of this preferences.
     */
    fun getFloatOrElse(key: String, defaultValue: () -> Float): Float =
        getFloatOrDefault(key, defaultValue())

    /**
     * Retrieve a [Long] value from the preferences.
     * @param key name of the preference to retrieve.
     * @return preference value if it exists, or null.
     * @throws UnsupportedOperationException if [Long] is not a supported type of this preferences.
     */
    fun getLong(key: String): Long?

    /**
     * Retrieve a [Long] value from the preferences, providing default value as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if [Long] is not a supported type of this preferences.
     */
    fun getLongOrDefault(key: String, defaultValue: Long): Long =
        findValue(key, defaultValue) { getLong(it) }

    /**
     * Retrieve a [Long] value from the preferences, calling default value getter as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if [Long] is not a supported type of this preferences.
     */
    fun getLongOrElse(key: String, defaultValue: () -> Long): Long =
        getLongOrDefault(key, defaultValue())

    /**
     * Retrieve an [Int] value from the preferences.
     * @param key name of the preference to retrieve.
     * @return preference value if it exists, or null.
     * @throws UnsupportedOperationException if [Int] is not a supported type of this preferences.
     */
    fun getInt(key: String): Int?

    /**
     * Retrieve an [Int] value from the preferences, providing default value as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if [Int] is not a supported type of this preferences.
     */
    fun getIntOrDefault(key: String, defaultValue: Int): Int =
        findValue(key, defaultValue) { getInt(it) }

    /**
     * Retrieve an [Int] value from the preferences, calling default value getter as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if [Int] is not a supported type of this preferences.
     */
    fun getIntOrElse(key: String, defaultValue: () -> Int): Int =
        getIntOrDefault(key, defaultValue())

    /**
     * Retrieve a [Short] value from the preferences.
     * @param key name of the preference to retrieve.
     * @return preference value if it exists, or null.
     * @throws UnsupportedOperationException if [Short] is not a supported type of this preferences.
     */
    fun getShort(key: String): Short?

    /**
     * Retrieve a [Short] value from the preferences, providing default value as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if [Short] is not a supported type of this preferences.
     */
    fun getShortOrDefault(key: String, defaultValue: Short): Short =
        findValue(key, defaultValue) { getShort(it) }

    /**
     * Retrieve a [Short] value from the preferences, calling default value getter as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if [Short] is not a supported type of this preferences.
     */
    fun getShortOrElse(key: String, defaultValue: () -> Short): Short =
        getShortOrDefault(key, defaultValue())

    /**
     * Retrieve a [Byte] value from the preferences.
     * @param key name of the preference to retrieve.
     * @return preference value if it exists, or null.
     * @throws UnsupportedOperationException if [Byte] is not a supported type of this preferences.
     */
    fun getByte(key: String): Byte?

    /**
     * Retrieve a [Byte] value from the preferences, providing default value as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if [Byte] is not a supported type of this preferences.
     */
    fun getByteOrDefault(key: String, defaultValue: Byte): Byte =
        findValue(key, defaultValue) { getByte(it) }

    /**
     * Retrieve a [Byte] value from the preferences, calling default value getter as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if [Byte] is not a supported type of this preferences.
     */
    fun getByteOrElse(key: String, defaultValue: () -> Byte): Byte =
        getByteOrDefault(key, defaultValue())

    private inline fun <T> findValue(key: String, defaultValue: T, getValue: (key: String) -> T?): T {
        if (key !in this) {
            val value = getValue(key)
            if (value != null) {
                return value
            }
        }
        return defaultValue
    }
}
