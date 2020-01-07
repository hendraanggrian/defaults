@file:JvmMultifileClass
@file:JvmName("PrefsJvmKt")
@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.prefs.jvm

import com.hendraanggrian.prefs.EditablePrefs
import com.hendraanggrian.prefs.Prefs
import com.hendraanggrian.prefs.PrefsSaver
import java.io.File
import java.util.Properties
import java.util.prefs.Preferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Create a [PropertiesPrefs] from file.
 *
 * @param file source of this preferences.
 */
fun Prefs.Companion.of(file: File): PropertiesPrefs = PropertiesPrefs(file)

/**
 * Create a [PropertiesPrefs] from file.
 * Preferences created from this function will have an automatic value conversion.
 *
 * @param file source of this preferences.
 */
fun Prefs.Companion.safeOf(file: File): PropertiesPrefs = SafePropertiesPrefs(file)

/**
 * Convenient method to bind [PropertiesPrefs] to target.
 *
 * @param file source of this preferences.
 * @param target parent of fields that will be binded to.
 */
inline fun Prefs.Companion.bind(file: File, target: Any): PrefsSaver = of(file).bind(target)

/**
 * Convenient method to bind [PropertiesPrefs] to target.
 * Preferences created from this function will have an automatic value conversion.
 *
 * @param file source of this preferences.
 * @param target parent of fields that will be binded to.
 */
inline fun Prefs.Companion.safeBind(file: File, target: Any): PrefsSaver = safeOf(file).bind(target)

open class PropertiesPrefs internal constructor(private val targetFile: File) : EditablePrefs {
    private val nativeProperties: Properties = Properties()

    init {
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

internal class SafeJvmPrefs(nativePreferences: Preferences) : JvmPrefs(nativePreferences) {
    override fun getShort(key: String): Short? = get(key)?.toShort()
    override fun getByte(key: String): Byte? = get(key)?.toByte()
    override fun set(key: String, value: Short) = set(key, value.toString())
    override fun set(key: String, value: Byte) = set(key, value.toString())
}
