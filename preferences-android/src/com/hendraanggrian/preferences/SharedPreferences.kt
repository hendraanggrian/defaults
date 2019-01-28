package com.hendraanggrian.preferences

import android.content.SharedPreferences

/*
class AndroidPreferences(private val preferences: SharedPreferences) :
    Preferences<AndroidPreferences.Editor> {

    override fun contains(key: String): Boolean = preferences.contains(key)

    override fun getString(key: String): String? = preferences.getString(key, "")

    override fun SharedPreferences.Editor.setString(key: String, value: String?) {
        putString(key, value)
    }

    override fun SharedPreferences.Editor.setInt(key: String, value: Int) {
        putInt(key, value)
    }

    override fun getEditor(): SharedPreferences.Editor = preferences.edit()

    override fun SharedPreferences.Editor.save() {
        commit()
    }

    class Editor : SharedPreferences.Editor{

    }
}*/
