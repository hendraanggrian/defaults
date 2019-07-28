package local.adapter.jvm

import java.util.prefs.Preferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import local.LocalWriter
import local.ReadableLocal

open class LocalPreferences internal constructor(private val nativePreferences: Preferences) :
    ReadableLocal, LocalWriter {

    override fun contains(key: String): Boolean = nativePreferences.nodeExists(key)

    override fun get(key: String): String? =
        nativePreferences.get(key, null)

    override fun getOrDefault(key: String, defaultValue: String): String =
        nativePreferences.get(key, defaultValue)

    override fun getBoolean(key: String): Boolean? =
        nativePreferences.getBoolean(key, false)

    override fun getBooleanOrDefault(key: String, defaultValue: Boolean): Boolean =
        nativePreferences.getBoolean(key, defaultValue)

    override fun getDouble(key: String): Double? =
        nativePreferences.getDouble(key, 0.0)

    override fun getDoubleOrDefault(key: String, defaultValue: Double): Double =
        nativePreferences.getDouble(key, defaultValue)

    override fun getFloat(key: String): Float? =
        nativePreferences.getFloat(key, 0f)

    override fun getFloatOrDefault(key: String, defaultValue: Float): Float =
        nativePreferences.getFloat(key, defaultValue)

    override fun getLong(key: String): Long? =
        nativePreferences.getLong(key, 0L)

    override fun getLongOrDefault(key: String, defaultValue: Long): Long =
        nativePreferences.getLong(key, defaultValue)

    override fun getInt(key: String): Int? =
        nativePreferences.getInt(key, 0)

    override fun getIntOrDefault(key: String, defaultValue: Int): Int =
        nativePreferences.getInt(key, defaultValue)

    override fun getShort(key: String): Short? = throw UnsupportedOperationException()

    override fun getByte(key: String): Byte? = throw UnsupportedOperationException()

    override fun remove(key: String) = nativePreferences.remove(key)

    override fun clear() = nativePreferences.clear()

    override fun set(key: String, value: String) = nativePreferences.put(key, value)

    override fun set(key: String, value: Boolean) = nativePreferences.putBoolean(key, value)

    override fun set(key: String, value: Double) = nativePreferences.putDouble(key, value)

    override fun set(key: String, value: Float) = nativePreferences.putFloat(key, value)

    override fun set(key: String, value: Long) = nativePreferences.putLong(key, value)

    override fun set(key: String, value: Int) = nativePreferences.putInt(key, value)

    override fun set(key: String, value: Short): Unit = throw UnsupportedOperationException()

    override fun set(key: String, value: Byte): Unit = throw UnsupportedOperationException()

    override fun save() {
        GlobalScope.launch(Dispatchers.IO) {
            saveAsync()
        }
    }

    override fun saveAsync() {
        nativePreferences.run {
            sync()
            flush()
        }
    }
}
