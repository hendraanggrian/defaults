package com.hendraanggrian.local

import android.content.SharedPreferences

internal class AndroidLocal(private val preferences: SharedPreferences) :
    Local<AndroidLocal.Editor>,
    SharedPreferences by preferences {

    override fun getString(key: String): String = preferences.getString(key, null).orEmpty()

    override fun getInt(key: String): Int = preferences.getInt(key, 0)

    override fun getEditor(): Editor = Editor(preferences.edit())

    class Editor(private val editor: SharedPreferences.Editor) : Local.Editor,
        SharedPreferences.Editor by editor {

        override fun setString(key: String, value: String?) {
            editor.putString(key, value)
        }

        override fun setInt(key: String, value: Int) {
            editor.putInt(key, value)
        }

        override fun save() {
            editor.commit()
        }
    }
}