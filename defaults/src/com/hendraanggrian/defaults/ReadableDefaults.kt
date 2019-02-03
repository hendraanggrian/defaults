package com.hendraanggrian.defaults

/** Represents a set of key-value pairs used as local settings. */
interface ReadableDefaults {

    /** Checks if a setting exists. */
    operator fun contains(key: String): Boolean

    /** Returns string value. */
    operator fun get(key: String): String? = get(key)

    /** Returns string value, or [defaultValue] if no such value exists. */
    fun getOrDefault(key: String, defaultValue: String): String =
        findValue(key, defaultValue) { get(it) }

    /** Returns string value, or [defaultValue] if no such value exists. */
    fun getOrElse(key: String, defaultValue: () -> String): String =
        getOrDefault(key, defaultValue())

    /** Returns boolean value. */
    fun getBoolean(key: String): Boolean?

    /** Returns boolean value, or [defaultValue] if no such value exists. */
    fun getBooleanOrDefault(key: String, defaultValue: Boolean): Boolean =
        findValue(key, defaultValue) { getBoolean(it) }

    /** Returns boolean value, or [defaultValue] if no such value exists. */
    fun getBooleanOrElse(key: String, defaultValue: () -> Boolean): Boolean =
        getBooleanOrDefault(key, defaultValue())

    /** Returns double value. */
    fun getDouble(key: String): Double?

    /** Returns double value, or [defaultValue] if no such value exists. */
    fun getDoubleOrDefault(key: String, defaultValue: Double): Double =
        findValue(key, defaultValue) { getDouble(it) }

    /** Returns double value, or [defaultValue] if no such value exists. */
    fun getDoubleOrElse(key: String, defaultValue: () -> Double): Double =
        getDoubleOrDefault(key, defaultValue())

    /** Returns float value. */
    fun getFloat(key: String): Float?

    /** Returns float value, or [defaultValue] if no such value exists. */
    fun getFloatOrDefault(key: String, defaultValue: Float): Float =
        findValue(key, defaultValue) { getFloat(it) }

    /** Returns float value, or [defaultValue] if no such value exists. */
    fun getFloatOrElse(key: String, defaultValue: () -> Float): Float =
        getFloatOrDefault(key, defaultValue())

    /** Returns long value. */
    fun getLong(key: String): Long?

    /** Returns Long value, or [defaultValue] if no such value exists. */
    fun getLongOrDefault(key: String, defaultValue: Long): Long =
        findValue(key, defaultValue) { getLong(it) }

    /** Returns Long value, or [defaultValue] if no such value exists. */
    fun getLongOrElse(key: String, defaultValue: () -> Long): Long =
        getLongOrDefault(key, defaultValue())

    /** Returns int value. */
    fun getInt(key: String): Int?

    /** Returns int value, or [defaultValue] if no such value exists. */
    fun getIntOrDefault(key: String, defaultValue: Int): Int =
        findValue(key, defaultValue) { getInt(it) }

    /** Returns int value, or [defaultValue] if no such value exists. */
    fun getIntOrElse(key: String, defaultValue: () -> Int): Int =
        getIntOrDefault(key, defaultValue())

    /** Returns short value. */
    fun getShort(key: String): Short?

    /** Returns short value, or [defaultValue] if no such value exists. */
    fun getShortOrDefault(key: String, defaultValue: Short): Short =
        findValue(key, defaultValue) { getShort(it) }

    /** Returns short value, or [defaultValue] if no such value exists. */
    fun getShortOrElse(key: String, defaultValue: () -> Short): Short =
        getShortOrDefault(key, defaultValue())

    /** Returns byte value. */
    fun getByte(key: String): Byte?

    /** Returns byte value, or [defaultValue] if no such value exists. */
    fun getByteOrDefault(key: String, defaultValue: Byte): Byte =
        findValue(key, defaultValue) { getByte(it) }

    /** Returns byte value, or [defaultValue] if no such value exists. */
    fun getByteOrElse(key: String, defaultValue: () -> Byte): Byte =
        getByteOrDefault(key, defaultValue())

    private inline fun <T> findValue(
        key: String,
        defaultValue: T,
        getValue: (String) -> T?
    ): T {
        if (key !in this) {
            val value = getValue(key)
            if (value != null) {
                return value
            }
        }
        return defaultValue
    }
}