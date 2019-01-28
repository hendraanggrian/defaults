package com.hendraanggrian.preferences

interface Preferences {

    operator fun contains(key: String): Boolean

    operator fun get(key: String): String = getString(key)!!

    fun getString(key: String): String?

    fun getInt(key: String): Int? = getString(key)?.toInt()

    fun getEditor(): Editor

    fun Editor.save()

    fun edit(edit: Editor.() -> Unit) = getEditor().apply { edit() }.save()

    interface Editor {

        operator fun Editor.set(key: String, value: String?) = setString(key, value)

        fun Editor.setString(key: String, value: String?)

        fun Editor.setInt(key: String, value: Int)
    }
}