package com.hendraanggrian.defaults

import org.junit.Before
import org.junit.Test
import java.io.File
import java.util.prefs.Preferences
import kotlin.test.assertEquals

class JvmDefaultsTest {

    private lateinit var testFile: File

    @Before
    fun createTest() {
        testFile = File("test.properties")
        if (testFile.exists()) {
            testFile.delete()
        }
    }

    @Test
    fun file() {
        val defaults = Defaults[testFile]
        defaults["name"] = "Hendra"
        defaults["age"] = 25
        assertEquals("Hendra", defaults["name"])
        assertEquals(25, defaults.getInt("age"))
    }

    @Test
    fun preferences() {
        val defaults = Defaults[Preferences.userRoot().node(Defaults.TAG)]
        defaults["name"] = "Hendra"
        defaults["age"] = 25
        assertEquals("Hendra", defaults["name"])
        assertEquals(25, defaults.getInt("age"))
    }
}