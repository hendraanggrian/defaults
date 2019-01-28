package com.hendraanggrian.local

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

    override fun getString(key: String): String = properties.getProperty(key)

    override fun getEditor(): Editor = Editor()

    inner class Editor : LocalSettings.Editor {

        override fun setString(key: String, value: String?) {
            properties.setProperty(key, value)
        }

        override fun save() {
            file.outputStream().use {
                properties.save(it, null)
            }
        }
    }
}