package com.hendraanggrian.defaults

/**
 * Represents a set of key-value pairs used as local settings.
 */
interface ReadableDefaults {

    /** Checks if a setting exists. */
    operator fun contains(key: String): Boolean

    /** Returns non-null string value. */
    operator fun get(key: String): String? = get(key)

    fun get(key: String, def: String?): String?

    fun getBoolean(key: String): Boolean

    fun getBoolean(key: String, def: Boolean): Boolean

    fun getDouble(key: String): Double

    fun getDouble(key: String, def: Double): Double

    fun getFloat(key: String): Float

    fun getFloat(key: String, def: Float): Float

    fun getLong(key: String): Long

    fun getLong(key: String, def: Long): Long

    fun getInt(key: String): Int

    fun getInt(key: String, def: Int): Int

    fun getShort(key: String): Short

    fun getShort(key: String, def: Short): Short

    fun getByte(key: String): Byte

    fun getByte(key: String, def: Byte): Byte
}