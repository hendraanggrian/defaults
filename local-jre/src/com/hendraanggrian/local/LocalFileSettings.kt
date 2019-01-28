package com.hendraanggrian.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.util.Properties

infix fun LocalSettings.Companion.file(file: File): LocalSettings<*> = LocalFileSettings(file)

private class LocalFileSettings(private val file: File) : LocalSettings<LocalFileSettings.Editor> {

    private val properties = Properties()

    init {
        if (!file.exists()) {
            file.createNewFile()
        }
        file.inputStream().use { properties.load(it) }
    }

    override fun contains(key: String): Boolean = properties.containsKey(key)

    override fun getString(key: String): String? = getString(key, null)

    override fun getString(key: String, defaultValue: String?): String? =
        properties.getProperty(key) ?: defaultValue

    override fun getInt(key: String): Int = getInt(key, 0)

    override fun getInt(key: String, defaultValue: Int): Int =
        properties.getProperty(key)?.toIntOrNull() ?: defaultValue

    override fun getLong(key: String): Long = getLong(key, 0L)

    override fun getLong(key: String, defaultValue: Long): Long =
        properties.getProperty(key)?.toLongOrNull() ?: defaultValue

    override fun getFloat(key: String): Float = getFloat(key, 0f)

    override fun getFloat(key: String, defaultValue: Float): Float =
        properties.getProperty(key)?.toFloatOrNull() ?: defaultValue

    override fun getBoolean(key: String): Boolean = getBoolean(key, false)

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean =
        properties.getProperty(key)?.toBoolean() ?: defaultValue

    override fun getEditor(): Editor = Editor()

    inner class Editor : LocalSettings.Editor {

        override fun setString(key: String, value: String?) {
            properties.setProperty(key, value)
        }

        override fun save() {
            GlobalScope.launch(Dispatchers.IO) {
                saveAsync()
            }
        }

        override fun saveAsync() {
            file.outputStream().use {
                properties.save(it, null)
            }
        }
    }
}