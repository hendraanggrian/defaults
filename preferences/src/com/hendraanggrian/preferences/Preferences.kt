package com.hendraanggrian.preferences

import java.io.File

interface Preferences<E : Preferences.Editor> {

    companion object {

        fun file(file: File): Preferences<*> = FilePreferences(file)
    }

    operator fun contains(key: String): Boolean

    operator fun get(key: String): String = getString(key)

    fun getString(key: String): String

    fun getInt(key: String): Int = getString(key).toInt()

    fun getEditor(): E

    fun edit(edit: (E.() -> Unit)) = getEditor().apply { edit() }.save()

    interface Editor {

        operator fun set(key: String, value: String?) = setString(key, value)

        fun setString(key: String, value: String?)

        fun setInt(key: String, value: Int)

        fun save()
    }
}