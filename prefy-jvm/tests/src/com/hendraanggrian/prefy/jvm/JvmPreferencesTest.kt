package com.hendraanggrian.prefy.jvm

import com.hendraanggrian.prefy.PreferencesLogger
import com.hendraanggrian.prefy.Prefy
import com.hendraanggrian.prefy.ReadablePreferences
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class JvmPreferencesTest {
    private lateinit var preferences: JvmPreferences

    @BeforeTest fun createTest() {
        Prefy.setLogger(PreferencesLogger.System)
        preferences = Prefy.userNode<ReadablePreferences>()
    }

    @Test fun jvm() {
        preferences["name"] = "Hendra"
        preferences.save()
        assertEquals("Hendra", preferences["name"])
    }
}