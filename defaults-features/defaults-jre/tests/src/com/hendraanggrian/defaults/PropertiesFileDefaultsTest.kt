package com.hendraanggrian.defaults

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
        val defaults = Defaults.from(testFile)
        defaults {
            it["name"] = "Hendra"
            it["age"] = 25
        }
        assertEquals("Hendra", defaults["name"])
        assertEquals(25, defaults.getInt("age"))
    }
}