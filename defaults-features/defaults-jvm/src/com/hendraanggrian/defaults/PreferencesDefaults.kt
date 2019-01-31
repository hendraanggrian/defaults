package com.hendraanggrian.defaults

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.prefs.Preferences

class PreferencesDefaults(val preferences: Preferences) : Defaults.NoEditor() {

    override fun contains(key: String): Boolean = preferences.nodeExists(key)

    override fun get(key: String): String? = get(key, null)

    override fun get(key: String, def: String?): String? = preferences.get(key, def)

    override fun getBoolean(key: String): Boolean = getBoolean(key, false)

    override fun getBoolean(key: String, def: Boolean): Boolean = preferences.getBoolean(key, def)

    override fun getDouble(key: String): Double = getDouble(key, 0.0)

    override fun getDouble(key: String, def: Double): Double = preferences.getDouble(key, def)

    override fun getFloat(key: String): Float = getFloat(key, 0f)

    override fun getFloat(key: String, def: Float): Float = preferences.getFloat(key, def)

    override fun getLong(key: String): Long = getLong(key, 0L)

    override fun getLong(key: String, def: Long): Long = preferences.getLong(key, def)

    override fun getInt(key: String): Int = getInt(key, 0)

    override fun getInt(key: String, def: Int): Int = preferences.getInt(key, def)

    override fun getShort(key: String): Short = throw UnsupportedOperationException()

    override fun getShort(key: String, def: Short): Short = throw UnsupportedOperationException()

    override fun getByte(key: String): Byte = throw UnsupportedOperationException()

    override fun getByte(key: String, def: Byte): Byte = throw UnsupportedOperationException()

    override fun minusAssign(key: String) = preferences.remove(key)

    override fun reset() = preferences.clear()

    override fun set(key: String, value: String?) = preferences.put(key, value)

    override fun set(key: String, value: Boolean) = preferences.putBoolean(key, value)

    override fun set(key: String, value: Double) = preferences.putDouble(key, value)

    override fun set(key: String, value: Float) = preferences.putFloat(key, value)

    override fun set(key: String, value: Long) = preferences.putLong(key, value)

    override fun set(key: String, value: Int) = preferences.putInt(key, value)

    override fun set(key: String, value: Short) = throw UnsupportedOperationException()

    override fun set(key: String, value: Byte) = set(key, value.toString())

    override fun save() {
        GlobalScope.launch(Dispatchers.IO) {
            saveAsync()
        }
    }

    override fun saveAsync() {
        preferences.run {
            sync()
            flush()
        }
    }
}