package com.hendraanggrian.prefs.jvm

import com.hendraanggrian.prefs.Prefs
import java.io.File
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PropertiesPrefsTest {
    private lateinit var propertiesPrefs: PropertiesPrefs

    @BeforeTest fun createTest() {
        Prefs.setLogger(Prefs.Logger.System)
        propertiesPrefs = Prefs[File("test.properties").apply { if (exists()) delete() }]
    }

    @Test fun properties() {
        propertiesPrefs["name"] = "Hendra"
        propertiesPrefs.save()
        assertEquals("Hendra", propertiesPrefs["name"])
    }
}