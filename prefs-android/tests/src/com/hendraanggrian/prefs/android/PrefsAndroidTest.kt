package com.hendraanggrian.prefs.android

import androidx.test.InstrumentationRegistry
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.hendraanggrian.prefs.Prefs
import org.junit.runner.RunWith
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@RunWith(AndroidJUnit4::class)
@LargeTest
class PrefsAndroidTest {
    private lateinit var prefs: SharedPrefs

    @BeforeTest fun createTest() {
        Prefs.setLogger(Prefs.Logger.AndroidDebug)
        prefs = Prefs.of(InstrumentationRegistry.getContext())
        prefs.edit { clear() }
    }

    @Test fun sharedPreferences() {
        assertNull(prefs["name"])
        assertNull(prefs.getInt("age"))
        prefs.edit {
            this["name"] = "Hendra"
            this["age"] = 25
        }
        assertEquals("Hendra", prefs["name"])
        assertEquals(25, prefs.getInt("age"))
    }
}