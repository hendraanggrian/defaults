package com.hendraanggrian.defaults

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

fun Defaults.Companion.from(sharedPreferences: SharedPreferences): Defaults<*> =
    SharedPreferencesDefaults(sharedPreferences)

@Suppress("NOTHING_TO_INLINE")
inline fun Defaults.Companion.from(context: Context): Defaults<*> =
    from(PreferenceManager.getDefaultSharedPreferences(context))

private class SharedPreferencesDefaults(private val preferences: SharedPreferences) :
    Defaults<SharedPreferencesDefaults.Editor>,
    SharedPreferences by preferences {

    override fun getString(key: String): String = getString(key, null).orEmpty()

    override fun getInt(key: String): Int = getInt(key, 0)

    override fun getLong(key: String): Long = getLong(key, 0L)

    override fun getFloat(key: String): Float = getFloat(key, 0f)

    override fun getBoolean(key: String): Boolean = getBoolean(key, false)

    override fun getEditor(): Editor =
        Editor(preferences.edit())

    private class Editor(private val editor: SharedPreferences.Editor) : Defaults.Editor,
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

        override fun set(key: String, value: Int) {
            editor.putInt(key, value)
        }

        override fun set(key: String, value: Long) {
            editor.putLong(key, value)
        }

        override fun set(key: String, value: Float) {
            editor.putFloat(key, value)
        }

        override fun set(key: String, value: Boolean) {
            editor.putBoolean(key, value)
        }

        override fun save() {
            editor.commit()
        }

        override fun saveAsync() {
            editor.apply()
        }
    }
}