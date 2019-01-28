package com.hendraanggrian.local

import java.io.File

interface Local<E : Local.Editor> {

    companion object {

        infix fun file(file: File): Local<*> = LocalFile(file)
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