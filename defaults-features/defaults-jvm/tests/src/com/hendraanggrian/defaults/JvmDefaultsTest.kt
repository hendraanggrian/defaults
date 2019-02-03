package com.hendraanggrian.defaults

import org.junit.Before
import org.junit.Test
import java.io.File
import java.util.prefs.Preferences
import kotlin.test.assertEquals

class JvmDefaultsTest {
    private lateinit var file: File
    private lateinit var preferences: Preferences

    @Before
    fun createTest() {
        file = File("test.properties").apply { if (exists()) delete() }
        preferences = Preferences.userRoot().node(Defaults.TAG)
    }

    @Test
    fun file() {
        val defaults = Defaults[file]
        defaults["name"] = "Hendra"
        defaults.saveAsync()
        assertEquals("Hendra", defaults["name"])
    }

    @Test
    fun preferences() {
        val defaults = Defaults[preferences]
        defaults["name"] = "Hendra"
        defaults.saveAsync()
        assertEquals("Hendra", defaults["name"])
    }
}