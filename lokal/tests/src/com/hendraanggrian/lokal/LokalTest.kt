package com.hendraanggrian.lokal

import org.junit.Before
import org.junit.Test
import java.io.File
import java.util.prefs.Preferences
import kotlin.test.assertEquals

class LokalTest {

    private lateinit var file: File
    private lateinit var preferences: Preferences

    @Before
    fun createTest() {
        file = File("test.properties").apply { if (exists()) delete() }
        preferences = Preferences.userRoot().node(Lokal::class.java.canonicalName)
    }

    @Test
    fun file() {
        val defaults = file.toLokal()
        defaults["name"] = "Hendra"
        defaults.save()
        assertEquals("Hendra", defaults["name"])
    }

    @Test
    fun preferences() {
        val defaults = preferences.toLokal()
        defaults["name"] = "Hendra"
        defaults.save()
        assertEquals("Hendra", defaults["name"])
    }
}