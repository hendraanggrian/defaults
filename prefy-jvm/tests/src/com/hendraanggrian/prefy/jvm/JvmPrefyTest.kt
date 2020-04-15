package com.hendraanggrian.prefy.jvm

import com.hendraanggrian.prefy.PreferencesLogger
import com.hendraanggrian.prefy.Prefy
import com.hendraanggrian.prefy.ReadablePreferences
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class JvmPrefyTest {
    private lateinit var jvmPrefs: JvmPreferences

    @BeforeTest fun createTest() {
        Prefy.setLogger(PreferencesLogger.System)
        jvmPrefs = Prefy.userNode<ReadablePreferences>()
    }

    @Test fun jvm() {
        jvmPrefs["name"] = "Hendra"
        jvmPrefs.save()
        assertEquals("Hendra", jvmPrefs["name"])
    }
}