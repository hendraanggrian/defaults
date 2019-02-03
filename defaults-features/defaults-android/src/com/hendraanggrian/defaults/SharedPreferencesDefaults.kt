package com.hendraanggrian.defaults

import android.content.SharedPreferences

class SharedPreferencesDefaults(private val nativePreferences: SharedPreferences) :
    Defaults<SharedPreferencesDefaults.Editor> {

    override fun contains(key: String): Boolean = key in nativePreferences

    override fun get(key: String): String? =
        nativePreferences.getString(key, null)

    override fun getOrDefault(key: String, defaultValue: String): String =
        nativePreferences.getString(key, defaultValue)!!

    override fun getBoolean(key: String): Boolean? =
        nativePreferences.getBoolean(key, false)

    override fun getBooleanOrDefault(key: String, defaultValue: Boolean): Boolean =
        nativePreferences.getBoolean(key, defaultValue)

    override fun getDouble(key: String): Double = throw UnsupportedOperationException()

    override fun getFloat(key: String): Float? =
        nativePreferences.getFloat(key, 0f)

    override fun getFloatOrDefault(key: String, defaultValue: Float): Float =
        nativePreferences.getFloat(key, defaultValue)

    override fun getLong(key: String): Long? = throw UnsupportedOperationException()

    override fun getInt(key: String): Int =
        nativePreferences.getInt(key, 0)

    override fun getIntOrDefault(key: String, defaultValue: Int): Int =
        nativePreferences.getInt(key, defaultValue)

    override fun getShort(key: String): Short = throw UnsupportedOperationException()

    override fun getByte(key: String): Byte = throw UnsupportedOperationException()

    override fun getEditor(): Editor = Editor(nativePreferences.edit())

    fun toSharedPreferences(): SharedPreferences = nativePreferences

    class Editor(private val nativeEditor: SharedPreferences.Editor) : Defaults.Editor {

        override fun minusAssign(key: String) {
            nativeEditor.remove(key)
        }

        override fun reset() {
            nativeEditor.clear()
        }

        override fun set(key: String, value: String?) {
            nativeEditor.putString(key, value)
        }

        override fun set(key: String, value: Boolean) {
            nativeEditor.putBoolean(key, value)
        }

        override fun set(key: String, value: Double) = throw UnsupportedOperationException()

        override fun set(key: String, value: Float) {
            nativeEditor.putFloat(key, value)
        }

        override fun set(key: String, value: Long) {
            nativeEditor.putLong(key, value)
        }

        override fun set(key: String, value: Int) {
            nativeEditor.putInt(key, value)
        }

        override fun set(key: String, value: Short) = throw UnsupportedOperationException()

        override fun set(key: String, value: Byte) = throw UnsupportedOperationException()

        override fun save() {
            nativeEditor.commit()
        }

        override fun saveAsync() {
            nativeEditor.apply()
        }
    }
}