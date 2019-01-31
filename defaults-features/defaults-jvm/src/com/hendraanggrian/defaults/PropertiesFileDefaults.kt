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

    override fun get(key: String): String? = get(key, null)

    override fun get(key: String, def: String?): String? =
        properties.getProperty(key) ?: def

    override fun getBoolean(key: String): Boolean = getBoolean(key, false)

    override fun getBoolean(key: String, def: Boolean): Boolean =
        properties.getProperty(key)?.toBoolean() ?: def

    override fun getDouble(key: String): Double = getDouble(key, 0.0)

    override fun getDouble(key: String, def: Double): Double =
        properties.getProperty(key)?.toDoubleOrNull() ?: def

    override fun getFloat(key: String): Float = getFloat(key, 0f)

    override fun getFloat(key: String, def: Float): Float =
        properties.getProperty(key)?.toFloatOrNull() ?: def

    override fun getLong(key: String): Long = getLong(key, 0L)

    override fun getLong(key: String, def: Long): Long =
        properties.getProperty(key)?.toLongOrNull() ?: def

    override fun getInt(key: String): Int = getInt(key, 0)

    override fun getInt(key: String, def: Int): Int =
        properties.getProperty(key)?.toIntOrNull() ?: def

    override fun getShort(key: String): Short = getShort(key, 0)

    override fun getShort(key: String, def: Short): Short =
        properties.getProperty(key)?.toShortOrNull() ?: def

    override fun getByte(key: String): Byte = getByte(key, 0)

    override fun getByte(key: String, def: Byte): Byte =
        properties.getProperty(key)?.toByteOrNull() ?: def

    override fun minusAssign(key: String) {
        properties.remove(key)
    }

    override fun reset() {
        properties.clear()
    }

    override fun set(key: String, value: String?) {
        properties.setProperty(key, value)
    }

    override fun set(key: String, value: Boolean) = set(key, value.toString())

    override fun set(key: String, value: Double) = set(key, value.toString())

    override fun set(key: String, value: Float) = set(key, value.toString())

    override fun set(key: String, value: Long) = set(key, value.toString())

    override fun set(key: String, value: Int) = set(key, value.toString())

    override fun set(key: String, value: Short) = set(key, value.toString())

    override fun set(key: String, value: Byte) = set(key, value.toString())

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