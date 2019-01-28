package com.hendraanggrian.preferences

import java.io.File
import java.util.Properties

/*
class FilePreferences(file: File) : Preferences<FilePreferences.Editor> {

    private val properties = Properties()

    init {
        if (!file.exists()) {
            file.createNewFile()
        }
        file.inputStream().use { properties.load(it) }
    }

    override fun contains(key: String): Boolean = properties.containsKey(key)

    override fun getString(key: String): String? = properties.getProperty(key)

    class Editor {

    }
}*/
