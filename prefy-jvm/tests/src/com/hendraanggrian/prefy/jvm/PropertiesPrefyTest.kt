package com.hendraanggrian.prefy.jvm

import com.hendraanggrian.prefy.PreferencesLogger
import com.hendraanggrian.prefy.Prefy
import java.io.File
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PropertiesPrefyTest {
    private lateinit var propertiesPrefs: PropertiesPreferences

    @BeforeTest fun createTest() {
        Prefy.setLogger(PreferencesLogger.System)
        propertiesPrefs = Prefy[File("test.properties").apply { if (exists()) delete() }]
    }

    @Test fun properties() {
        propertiesPrefs["name"] = "Hendra"
        propertiesPrefs.save()
        assertEquals("Hendra", propertiesPrefs["name"])
    }
}