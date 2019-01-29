package com.hendraanggrian.tools.defaults

import org.junit.Before
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class PropertiesFileDefaultsTest {

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
        val preferences = Defaults file testFile
        preferences.edit {
            set("name", "Hendra")
            set("age", 25)
        }
        assertEquals("Hendra", preferences["name"])
        assertEquals(25, preferences.getInt("age"))
    }
}