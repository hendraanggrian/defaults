package com.hendraanggrian.prefy.android

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.hendraanggrian.prefy.PreferencesLogger
import com.hendraanggrian.prefy.Prefy
import org.junit.runner.RunWith
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@LargeTest
@RunWith(AndroidJUnit4::class)
class AndroidPrefyTest {
    private lateinit var preferences: AndroidPreferences

    @BeforeTest fun createTest() {
        Prefy.setLogger(PreferencesLogger.Android)
        preferences = InstrumentationRegistry.getInstrumentation().context.preferences
        preferences.edit { clear() }
    }

    @Test fun sharedPreferences() {
        assertNull(preferences["name"])
        assertNull(preferences.getInt("age"))
        preferences.edit {
            this["name"] = "Hendra"
            this["age"] = 25
        }
        assertEquals("Hendra", preferences["name"])
        assertEquals(25, preferences.getInt("age"))
    }
}