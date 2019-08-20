package local.adapter.jvm

import local.Local
import org.junit.Before
import org.junit.Test
import java.io.File
import java.util.prefs.Preferences
import kotlin.test.assertEquals

class LocalTest {

    private lateinit var file: File
    private lateinit var preferences: Preferences

    @Before
    fun createTest() {
        file = File("test.properties").apply { if (exists()) delete() }
        preferences = Preferences.userRoot().node(Local::class.java.canonicalName)
    }

    @Test
    fun file() {
        val local = Local.of(file)
        local["name"] = "Hendra"
        local.save()
        assertEquals("Hendra", local["name"])
    }

    @Test
    fun preferences() {
        val local = Local.of(preferences)
        local["name"] = "Hendra"
        local.save()
        assertEquals("Hendra", local["name"])
    }
}