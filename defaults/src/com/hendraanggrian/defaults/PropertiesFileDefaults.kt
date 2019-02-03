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

    override fun getBoolean(key: String): Boolean? = throw UnsupportedOperationException()

    override fun getDouble(key: String): Double? = throw UnsupportedOperationException()

    override fun getFloat(key: String): Float? = throw UnsupportedOperationException()

    override fun getLong(key: String): Long? = throw UnsupportedOperationException()

    override fun getInt(key: String): Int? = throw UnsupportedOperationException()

    override fun getShort(key: String): Short? = throw UnsupportedOperationException()

    override fun getByte(key: String): Byte? = throw UnsupportedOperationException()

    override fun minusAssign(key: String) {
        properties.remove(key)
    }

    override fun clear() {
        properties.clear()
    }

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