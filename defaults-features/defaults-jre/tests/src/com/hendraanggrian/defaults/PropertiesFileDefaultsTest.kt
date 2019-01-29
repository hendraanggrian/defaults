package com.hendraanggrian.defaults

import com.hendraanggrian.defaults.file
import com.hendraanggrian.tools.defaults.Defaults
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
        val defaults = Defaults file testFile
        defaults {
            set("name", "Hendra")
            set("age", 25)
        }
        assertEquals("Hendra", defaults["name"])
        assertEquals(25, defaults.getInt("age"))
    }
}