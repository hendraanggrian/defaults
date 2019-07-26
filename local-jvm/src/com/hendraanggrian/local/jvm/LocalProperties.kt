package com.hendraanggrian.local.jvm

import com.hendraanggrian.local.WritableLocal
import java.io.File
import java.util.Properties
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

open class LocalProperties(private val nativeProperties: Properties, private val targetFile: File) :
    WritableLocal {

    constructor(targetFile: File) : this(Properties(), targetFile) {
        if (!targetFile.exists()) {
            targetFile.createNewFile()
        }
        targetFile.inputStream().use { nativeProperties.load(it) }
    }

    override fun contains(key: String): Boolean = nativeProperties.containsKey(key)

    override fun get(key: String): String? = nativeProperties.getProperty(key)

    override fun getOrDefault(key: String, defaultValue: String): String =
        nativeProperties.getProperty(key, defaultValue)

    override fun getBoolean(key: String): Boolean? = throw UnsupportedOperationException()

    override fun getDouble(key: String): Double? = throw UnsupportedOperationException()

    override fun getFloat(key: String): Float? = throw UnsupportedOperationException()

    override fun getLong(key: String): Long? = throw UnsupportedOperationException()

    override fun getInt(key: String): Int? = throw UnsupportedOperationException()

    override fun getShort(key: String): Short? = throw UnsupportedOperationException()

    override fun getByte(key: String): Byte? = throw UnsupportedOperationException()

    override fun remove(key: String) {
        nativeProperties.remove(key)
    }

    override fun clear() = nativeProperties.clear()

    override fun set(key: String, value: String) {
        nativeProperties.setProperty(key, value)
    }

    override fun set(key: String, value: Boolean): Unit = throw UnsupportedOperationException()

    override fun set(key: String, value: Double): Unit = throw UnsupportedOperationException()

    override fun set(key: String, value: Float): Unit = throw UnsupportedOperationException()

    override fun set(key: String, value: Long): Unit = throw UnsupportedOperationException()

    override fun set(key: String, value: Int): Unit = throw UnsupportedOperationException()

    override fun set(key: String, value: Short): Unit = throw UnsupportedOperationException()

    override fun set(key: String, value: Byte): Unit = throw UnsupportedOperationException()

    override fun save() {
        GlobalScope.launch(Dispatchers.IO) {
            saveAsync()
        }
    }

    override fun saveAsync() {
        targetFile.outputStream().use {
            nativeProperties.store(it, null)
        }
    }
}
