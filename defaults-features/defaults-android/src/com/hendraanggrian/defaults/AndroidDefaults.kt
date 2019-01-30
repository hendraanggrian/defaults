@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.defaults

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/** Creates defaults instance from shared preferences. */
inline fun Defaults.Companion.from(sharedPreferences: SharedPreferences): AndroidDefaults =
    AndroidDefaults(sharedPreferences)

/** Creates defaults instance from default shared preferences in context. */
inline fun Defaults.Companion.from(context: Context): AndroidDefaults =
    from(PreferenceManager.getDefaultSharedPreferences(context))

inline fun AndroidDefaults.addListener(
    noinline listener: (Defaults<*>, String) -> Unit
): SharedPreferences.OnSharedPreferenceChangeListener =
    SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
        listener(Defaults.from(sharedPreferences), key)
    }.also {
        sharedPreferences.registerOnSharedPreferenceChangeListener(it)
    }

inline fun AndroidDefaults.removeListener(
    listener: SharedPreferences.OnSharedPreferenceChangeListener
) = sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)

class AndroidDefaults(val sharedPreferences: SharedPreferences) : Defaults<AndroidDefaults.Editor>,
    SharedPreferences by sharedPreferences {

    override fun getString(key: String): String = getString(key, null).orEmpty()

    override fun getBoolean(key: String): Boolean = getBoolean(key, false)

    override fun getDouble(key: String): Double = throw UnsupportedOperationException()

    override fun getDouble(key: String, defaultValue: Double): Double =
        throw UnsupportedOperationException()

    override fun getLong(key: String): Long = getLong(key, 0L)

    override fun getFloat(key: String): Float = getFloat(key, 0f)

    override fun getInt(key: String): Int = getInt(key, 0)

    override fun getShort(key: String): Short = throw UnsupportedOperationException()

    override fun getShort(key: String, defaultValue: Short): Short =
        throw UnsupportedOperationException()

    override fun getByte(key: String): Byte = throw UnsupportedOperationException()

    override fun getByte(key: String, defaultValue: Byte): Byte =
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