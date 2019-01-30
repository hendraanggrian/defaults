@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.defaults

import com.hendraanggrian.defaults.internal.SimpleDefaults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.util.Properties

/** Creates defaults instance from file. */
inline fun Defaults.Companion.from(file: File): Defaults<*> = PropertiesFileDefaults(file)

class PropertiesFileDefaults(private val file: File) : SimpleDefaults() {
    private val properties = Properties()

    init {
        if (!file.exists()) {
            file.createNewFile()
        }
        file.inputStream().use { properties.load(it) }
    }

    override fun contains(key: String): Boolean = properties.containsKey(key)

    override fun getString(key: String): String? = getString(key, null)

    override fun getString(key: String, defaultValue: String?): String? =
        properties.getProperty(key) ?: defaultValue

    override fun getBoolean(key: String): Boolean = getBoolean(key, false)

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean =
        properties.getProperty(key)?.toBoolean() ?: defaultValue

    override fun getDouble(key: String): Double = getDouble(key, 0.0)

    override fun getDouble(key: String, defaultValue: Double): Double =
        properties.getProperty(key)?.toDoubleOrNull() ?: defaultValue

    override fun getFloat(key: String): Float = getFloat(key, 0f)

    override fun getFloat(key: String, defaultValue: Float): Float =
        properties.getProperty(key)?.toFloatOrNull() ?: defaultValue

    override fun getLong(key: String): Long = getLong(key, 0L)

    override fun getLong(key: String, defaultValue: Long): Long =
        properties.getProperty(key)?.toLongOrNull() ?: defaultValue

    override fun getInt(key: String): Int = getInt(key, 0)

    override fun getInt(key: String, defaultValue: Int): Int =
        properties.getProperty(key)?.toIntOrNull() ?: defaultValue

    override fun getShort(key: String): Short = getShort(key, 0)

    override fun getShort(key: String, defaultValue: Short): Short =
        properties.getProperty(key)?.toShortOrNull() ?: defaultValue

    override fun getByte(key: String): Byte = getByte(key, 0)

    override fun getByte(key: String, defaultValue: Byte): Byte =
        properties.getProperty(key)?.toByteOrNull() ?: defaultValue

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