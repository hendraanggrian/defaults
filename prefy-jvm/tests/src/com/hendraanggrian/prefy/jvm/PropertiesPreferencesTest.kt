package com.hendraanggrian.prefy.jvm

import com.hendraanggrian.prefy.PreferencesLogger
import com.hendraanggrian.prefy.Prefy
import java.io.File
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PropertiesPreferencesTest {
    private lateinit var preferences: PropertiesPreferences

    @BeforeTest fun createTest() {
        Prefy.setLogger(PreferencesLogger.System)
        preferences = Prefy[File("test.properties").apply { if (exists()) delete() }]
    }

    @Test fun properties() {
        preferences["name"] = "Hendra"
        preferences.save()
        assertEquals("Hendra", preferences["name"])
    }
}