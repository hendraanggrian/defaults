@file:JvmMultifileClass
@file:JvmName("PrefyJvmKt")
@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.prefy.jvm

import com.hendraanggrian.prefy.Prefy
import com.hendraanggrian.prefy.WritablePreferences
import java.io.File
import java.util.Properties

/**
 * Create a [PropertiesPreferences] from source [File].
 * @param source file containing [Properties] elements.
 * @return preferences that reads/writes to [Properties].
 */
operator fun Prefy.get(source: File): PropertiesPreferences = PropertiesPreferences(source)

/** A wrapper of [Properties] with [WritablePreferences] implementation. */
class PropertiesPreferences internal constructor(private val targetFile: File) :
    Properties(), WritablePreferences {

    init {
        if (!targetFile.exists()) targetFile.createNewFile()
        targetFile.inputStream().use { load(it) }
    }

    override fun contains(key: String): Boolean = containsKey(key)

    override fun get(key: String): String? = getProperty(key)
    override fun getOrDefault(key: String, defaultValue: String): String = getProperty(key, defaultValue)

    override fun getBoolean(key: String): Boolean? = throw UnsupportedOperationException()
    override fun getDouble(key: String): Double? = throw UnsupportedOperationException()
    override fun getFloat(key: String): Float? = throw UnsupportedOperationException()
    override fun getLong(key: String): Long? = throw UnsupportedOperationException()
    override fun getInt(key: String): Int? = throw UnsupportedOperationException()
    override fun getShort(key: String): Short? = throw UnsupportedOperationException()
    override fun getByte(key: String): Byte? = throw UnsupportedOperationException()

    override fun remove(key: String) {
        super.remove(key as Any)
    }

    override fun set(key: String, value: String?) {
        setProperty(key, value)
    }

    override fun set(key: String, value: Boolean): Unit = throw UnsupportedOperationException()
    override fun set(key: String, value: Double): Unit = throw UnsupportedOperationException()
    override fun set(key: String, value: Float): Unit = throw UnsupportedOperationException()
    override fun set(key: String, value: Long): Unit = throw UnsupportedOperationException()
    override fun set(key: String, value: Int): Unit = throw UnsupportedOperationException()
    override fun set(key: String, value: Short): Unit = throw UnsupportedOperationException()
    override fun set(key: String, value: Byte): Unit = throw UnsupportedOperationException()

    override fun save() = targetFile.outputStream().use { store(it, null) }
}
