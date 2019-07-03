package com.hendraanggrian.lokal.internal

import com.hendraanggrian.lokal.ReadableLokal

/** Provides support to read string as boolean. */
interface BooleanReadableLokalSupport : ReadableLokal {
    override fun getBoolean(key: String): Boolean? = get(key)?.toBoolean()
    override fun getBooleanOrDefault(key: String, defaultValue: Boolean): Boolean =
        getOrDefault(key, defaultValue.toString()).toBoolean()
}

/** Provides support to read string as double. */
interface DoubleReadableLokalSupport : ReadableLokal {
    override fun getDouble(key: String): Double? = get(key)?.toDoubleOrNull()
    override fun getDoubleOrDefault(key: String, defaultValue: Double): Double =
        getOrDefault(key, defaultValue.toString()).toDouble()
}

/** Provides support to read string as float. */
interface FloatReadableLokalSupport : ReadableLokal {
    override fun getFloat(key: String): Float? = get(key)?.toFloatOrNull()
    override fun getFloatOrDefault(key: String, defaultValue: Float): Float =
        getOrDefault(key, defaultValue.toString()).toFloat()
}

/** Provides support to read string as long. */
interface LongReadableLokalSupport : ReadableLokal {
    override fun getLong(key: String): Long? = get(key)?.toLongOrNull()
    override fun getLongOrDefault(key: String, defaultValue: Long): Long =
        getOrDefault(key, defaultValue.toString()).toLong()
}

/** Provides support to read string as int. */
interface IntReadableLokalSupport : ReadableLokal {
    override fun getInt(key: String): Int? = get(key)?.toIntOrNull()
    override fun getIntOrDefault(key: String, defaultValue: Int): Int =
        getOrDefault(key, defaultValue.toString()).toInt()
}

/** Provides support to read string as short. */
interface ShortReadableLokalSupport : ReadableLokal {
    override fun getShort(key: String): Short? = get(key)?.toShortOrNull()
    override fun getShortOrDefault(key: String, defaultValue: Short): Short =
        getOrDefault(key, defaultValue.toString()).toShort()
}

/** Provides support to read string as byte. */
interface ByteReadableLokalSupport : ReadableLokal {
    override fun getByte(key: String): Byte? = get(key)?.toByteOrNull()
    override fun getByteOrDefault(key: String, defaultValue: Byte): Byte =
        getOrDefault(key, defaultValue.toString()).toByte()
}