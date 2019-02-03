package com.hendraanggrian.defaults

import org.junit.Before
import org.junit.Test
import java.io.File
import java.util.prefs.Preferences
import kotlin.test.assertEquals

class DefaultsTest {

    private lateinit var file: File
    private lateinit var preferences: Preferences

    @Before
    fun createTest() {
        file = File("test.properties").apply { if (exists()) delete() }
        preferences = Preferences.userRoot().node(Defaults::class.java.canonicalName)
    }

    @Test
    fun file() {
        val defaults = file.toDefaults()
        defaults["name"] = "Hendra"
        defaults.save()
        assertEquals("Hendra", defaults["name"])
    }

    @Test
    fun preferences() {
        val defaults = preferences.toDefaults()
        defaults["name"] = "Hendra"
        defaults.save()
        assertEquals("Hendra", defaults["name"])
    }
}