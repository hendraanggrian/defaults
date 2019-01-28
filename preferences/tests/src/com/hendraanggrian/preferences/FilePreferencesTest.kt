package com.hendraanggrian.preferences

import org.junit.Before
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class FilePreferencesTest {

    private lateinit var testFile: File

    @Before
    fun createTest() {
        testFile = File("test.properties")
        if (testFile.exists()) {
            testFile.delete()
        }
    }

    @Test
    fun test() {
        val preferences = Preferences.file(testFile)
        preferences.edit {
            set("name", "Hendra")
            setInt("age", 25)
        }
        assertEquals("Hendra", preferences["name"])
        assertEquals(25, preferences.getInt("age"))
    }
}