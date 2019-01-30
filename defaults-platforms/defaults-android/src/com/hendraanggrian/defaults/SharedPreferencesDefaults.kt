package com.hendraanggrian.defaults

import android.content.SharedPreferences

class SharedPreferencesDefaults(private val sharedPreferences: SharedPreferences) :
    Defaults<SharedPreferencesDefaults.Editor>,
    SharedPreferences by sharedPreferences {

    override fun get(key: String): String? = getString(key, null)

    override fun get(key: String, def: String?): String? = sharedPreferences.getString(key, def)

    override fun getBoolean(key: String): Boolean = getBoolean(key, false)

    override fun getDouble(key: String): Double = throw UnsupportedOperationException()

    override fun getDouble(key: String, def: Double): Double =
        throw UnsupportedOperationException()

    override fun getLong(key: String): Long = getLong(key, 0L)

    override fun getFloat(key: String): Float = getFloat(key, 0f)

    override fun getInt(key: String): Int = getInt(key, 0)

    override fun getShort(key: String): Short = throw UnsupportedOperationException()

    override fun getShort(key: String, def: Short): Short =
        throw UnsupportedOperationException()

    override fun getByte(key: String): Byte = throw UnsupportedOperationException()

    override fun getByte(key: String, def: Byte): Byte =
        throw UnsupportedOperationException()

    override fun getEditor(): Editor =
        Editor(sharedPreferences.edit())

    class Editor(private val editor: SharedPreferences.Editor) : Defaults.Editor,
        SharedPreferences.Editor by editor {

        override fun minusAssign(key: String) {
            editor.remove(key)
        }

        override fun reset() {
            editor.clear()
        }

        override fun set(key: String, value: String?) {
            editor.putString(key, value)
        }

        override fun set(key: String, value: Boolean) {
            editor.putBoolean(key, value)
        }

        override fun set(key: String, value: Double) = throw UnsupportedOperationException()

        override fun set(key: String, value: Float) {
            editor.putFloat(key, value)
        }

        override fun set(key: String, value: Long) {
            editor.putLong(key, value)
        }

        override fun set(key: String, value: Int) {
            editor.putInt(key, value)
        }

        override fun set(key: String, value: Short) = throw UnsupportedOperationException()

        override fun set(key: String, value: Byte) = throw UnsupportedOperationException()

        override fun save() {
            editor.commit()
        }

        override fun saveAsync() {
            editor.apply()
        }
    }
}