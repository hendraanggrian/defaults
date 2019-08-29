package com.hendraanggrian.prefs.android

import androidx.test.InstrumentationRegistry
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.hendraanggrian.prefs.Prefs
import com.hendraanggrian.prefs.WritablePrefs
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertNull

@RunWith(AndroidJUnit4::class)
@LargeTest
class PrefsAndroidTest {

    private lateinit var prefs: WritablePrefs

    @Before
    fun createTest() {
        prefs = Prefs.of(InstrumentationRegistry.getContext())
        prefs.editor { clear() }
    }

    @Test
    fun sharedPreferences() {
        assertNull(prefs["name"])
        assertNull(prefs.getInt("age"))
        prefs.editor {
            this["name"] = "Hendra"
            this["age"] = 25
        }
        assertEquals("Hendra", prefs["name"])
        assertEquals(25, prefs.getInt("age"))
    }
}