package com.hendraanggrian.prefs.jvm

import com.hendraanggrian.prefs.Prefs
import org.junit.Before
import org.junit.Test
import java.io.File
import java.util.prefs.Preferences
import kotlin.test.assertEquals

class PrefsJvmTest {

    private lateinit var file: File
    private lateinit var preferences: Preferences

    @Before
    fun createTest() {
        file = File("test.properties").apply { if (exists()) delete() }
        preferences = Preferences.userRoot().node(Prefs::class.java.canonicalName)
    }

    @Test
    fun file() {
        val prefs = Prefs.of(file)
        prefs["name"] = "Hendra"
        prefs.save()
        assertEquals("Hendra", prefs["name"])
    }

    @Test
    fun preferences() {
        val prefs = Prefs.of(preferences)
        prefs["name"] = "Hendra"
        prefs.save()
        assertEquals("Hendra", prefs["name"])
    }
}