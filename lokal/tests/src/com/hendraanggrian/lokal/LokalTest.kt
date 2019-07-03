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
        val lokal = file.toLokal()
        lokal["name"] = "Hendra"
        lokal.save()
        assertEquals("Hendra", lokal["name"])
    }

    @Test
    fun preferences() {
        val lokal = preferences.toLokal()
        lokal["name"] = "Hendra"
        lokal.save()
        assertEquals("Hendra", lokal["name"])
    }
}