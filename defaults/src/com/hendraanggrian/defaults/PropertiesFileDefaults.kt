package com.hendraanggrian.defaults

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.util.Properties

class PropertiesFileDefaults(val file: File) : WritableDefaults {
    private val properties = Properties()

    init {
        if (!file.exists()) {
            file.createNewFile()
        }
        file.inputStream().use { properties.load(it) }
    }

    override fun contains(key: String): Boolean = properties.containsKey(key)

    override fun get(key: String): String? = properties.getProperty(key)

    override fun getOrDefault(key: String, defaultValue: String): String =
        properties.getProperty(key, defaultValue)

    override fun getBoolean(key: String): Boolean? = get(key)?.toBoolean()

    override fun getBooleanOrDefault(key: String, defaultValue: Boolean): Boolean =
        getOrDefault(key, defaultValue.toString()).toBoolean()

    override fun getDouble(key: String): Double? = get(key)?.toDoubleOrNull()

    override fun getDoubleOrDefault(key: String, defaultValue: Double): Double =
        getOrDefault(key, defaultValue.toString()).toDouble()

    override fun getFloat(key: String): Float? = get(key)?.toFloatOrNull()

    override fun getFloatOrDefault(key: String, defaultValue: Float): Float =
        getOrDefault(key, defaultValue.toString()).toFloat()

    override fun getLong(key: String): Long? = get(key)?.toLongOrNull()

    override fun getLongOrDefault(key: String, defaultValue: Long): Long =
        getOrDefault(key, defaultValue.toString()).toLong()

    override fun getInt(key: String): Int? = get(key)?.toIntOrNull()

    override fun getIntOrDefault(key: String, defaultValue: Int): Int =
        getOrDefault(key, defaultValue.toString()).toInt()

    override fun getShort(key: String): Short? = get(key)?.toShortOrNull()

    override fun getShortOrDefault(key: String, defaultValue: Short): Short =
        getOrDefault(key, defaultValue.toString()).toShort()

    override fun getByte(key: String): Byte? = get(key)?.toByteOrNull()

    override fun getByteOrDefault(key: String, defaultValue: Byte): Byte =
        getOrDefault(key, defaultValue.toString()).toByte()

    override fun minusAssign(key: String) {
        properties.remove(key)
    }

    override fun clear() = properties.clear()

    override fun set(key: String, value: String?) {
        properties.setProperty(key, value)
    }

    override fun set(key: String, value: Boolean) = throw UnsupportedOperationException()

    override fun set(key: String, value: Double) = throw UnsupportedOperationException()

    override fun set(key: String, value: Float) = throw UnsupportedOperationException()

    override fun set(key: String, value: Long) = throw UnsupportedOperationException()

    override fun set(key: String, value: Int) = throw UnsupportedOperationException()

    override fun set(key: String, value: Short) = throw UnsupportedOperationException()

    override fun set(key: String, value: Byte) = throw UnsupportedOperationException()

    override fun save() {
        GlobalScope.launch(Dispatchers.IO) {
            saveAsync()
        }
    }

    override fun saveAsync() {
        file.outputStream().use {
            properties.store(it, null)
        }
    }
}