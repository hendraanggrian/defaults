package com.hendraanggrian.local

interface Local<E : Local.Editor> {

    companion object;

    operator fun contains(key: String): Boolean

    operator fun get(key: String): String = getString(key)

    fun getString(key: String): String

    fun getInt(key: String): Int = getString(key).toInt()

    fun getLong(key: String): Long = getString(key).toLong()

    fun getFloat(key: String): Float = getString(key).toFloat()

    fun getBoolean(key: String): Boolean = getString(key).toBoolean()

    fun getEditor(): E

    fun edit(edit: (E.() -> Unit)) = getEditor().apply { edit() }.save()

    interface Editor {

        operator fun set(key: String, value: String?) = setString(key, value)

        fun setString(key: String, value: String?)

        fun setInt(key: String, value: Int) = setString(key, value.toString())

        fun setLong(key: String, value: Long) = setString(key, value.toString())

        fun setFloat(key: String, value: Float) = setString(key, value.toString())

        fun setBoolean(key: String, value: Boolean) = setString(key, value.toString())

        fun save()
    }
}