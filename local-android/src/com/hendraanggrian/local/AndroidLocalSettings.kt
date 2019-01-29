package com.hendraanggrian.local

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

fun SharedPreferences.localSettings(): LocalSettings<*> = AndroidLocalSettings(this)

@Suppress("NOTHING_TO_INLINE")
inline fun Context.localSettings(): LocalSettings<*> =
    PreferenceManager.getDefaultSharedPreferences(this).localSettings()

private class AndroidLocalSettings(private val preferences: SharedPreferences) :
    LocalSettings<AndroidLocalSettings.Editor>,
    SharedPreferences by preferences {

    override fun getString(key: String): String = getString(key, null).orEmpty()

    override fun getInt(key: String): Int = getInt(key, 0)

    override fun getLong(key: String): Long = getLong(key, 0L)

    override fun getFloat(key: String): Float = getFloat(key, 0f)

    override fun getBoolean(key: String): Boolean = getBoolean(key, false)

    override fun getEditor(): Editor = Editor(preferences.edit())

    class Editor(private val editor: SharedPreferences.Editor) : LocalSettings.Editor,
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