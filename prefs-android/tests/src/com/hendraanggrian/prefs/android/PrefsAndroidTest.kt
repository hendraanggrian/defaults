package com.hendraanggrian.prefs.android

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
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
        Prefs.setLogger(Prefs.Logger.Android)
        prefs = Prefs[InstrumentationRegistry.getInstrumentation().context]
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