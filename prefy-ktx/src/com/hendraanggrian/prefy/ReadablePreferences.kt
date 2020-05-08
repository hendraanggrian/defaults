package com.hendraanggrian.prefy

/**
 * Retrieve a [String] value from the preferences, calling default value getter as a fallback.
 * @param key name of the preference to retrieve.
 * @param defaultValue value to return if this preference does not exist.
 * @return preference value if it exists, or default value.
 */
inline fun ReadablePreferences.getOrElse(key: String, defaultValue: () -> String): String =
    getOrDefault(key, defaultValue())

/**
 * Retrieve a [Boolean] value from the preferences, calling default value getter as a fallback.
 * @param key name of the preference to retrieve.
 * @param defaultValue value to return if this preference does not exist.
 * @return preference value if it exists, or default value.
 * @throws UnsupportedOperationException if [Boolean] is not a supported type of this preferences.
 */
inline fun ReadablePreferences.getBooleanOrElse(key: String, defaultValue: () -> Boolean): Boolean =
    getBooleanOrDefault(key, defaultValue())

/**
 * Retrieve a [Double] value from the preferences, calling default value getter as a fallback.
 * @param key name of the preference to retrieve.
 * @param defaultValue value to return if this preference does not exist.
 * @return preference value if it exists, or default value.
 * @throws UnsupportedOperationException if [Double] is not a supported type of this preferences.
 */
inline fun ReadablePreferences.getDoubleOrElse(key: String, defaultValue: () -> Double): Double =
    getDoubleOrDefault(key, defaultValue())

/**
 * Retrieve a [Float] value from the preferences, calling default value getter as a fallback.
 * @param key name of the preference to retrieve.
 * @param defaultValue value to return if this preference does not exist.
 * @return preference value if it exists, or default value.
 * @throws UnsupportedOperationException if [Float] is not a supported type of this preferences.
 */
inline fun ReadablePreferences.getFloatOrElse(key: String, defaultValue: () -> Float): Float =
    getFloatOrDefault(key, defaultValue())

/**
 * Retrieve a [Long] value from the preferences, calling default value getter as a fallback.
 * @param key name of the preference to retrieve.
 * @param defaultValue value to return if this preference does not exist.
 * @return preference value if it exists, or default value.
 * @throws UnsupportedOperationException if [Long] is not a supported type of this preferences.
 */
inline fun ReadablePreferences.getLongOrElse(key: String, defaultValue: () -> Long): Long =
    getLongOrDefault(key, defaultValue())

/**
 * Retrieve an [Int] value from the preferences, calling default value getter as a fallback.
 * @param key name of the preference to retrieve.
 * @param defaultValue value to return if this preference does not exist.
 * @return preference value if it exists, or default value.
 * @throws UnsupportedOperationException if [Int] is not a supported type of this preferences.
 */
inline fun ReadablePreferences.getIntOrElse(key: String, defaultValue: () -> Int): Int =
    getIntOrDefault(key, defaultValue())

/**
 * Retrieve a [Short] value from the preferences, calling default value getter as a fallback.
 * @param key name of the preference to retrieve.
 * @param defaultValue value to return if this preference does not exist.
 * @return preference value if it exists, or default value.
 * @throws UnsupportedOperationException if [Short] is not a supported type of this preferences.
 */
inline fun ReadablePreferences.getShortOrElse(key: String, defaultValue: () -> Short): Short =
    getShortOrDefault(key, defaultValue())

/**
 * Retrieve a [Byte] value from the preferences, calling default value getter as a fallback.
 * @param key name of the preference to retrieve.
 * @param defaultValue value to return if this preference does not exist.
 * @return preference value if it exists, or default value.
 * @throws UnsupportedOperationException if [Byte] is not a supported type of this preferences.
 */
inline fun ReadablePreferences.getByteOrElse(key: String, defaultValue: () -> Byte): Byte =
    getByteOrDefault(key, defaultValue())