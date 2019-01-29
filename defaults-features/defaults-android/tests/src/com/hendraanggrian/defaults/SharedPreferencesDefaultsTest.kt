package com.hendraanggrian.defaults

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.test.InstrumentationRegistry
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.hendraanggrian.defaults.sharedPreferences
import com.hendraanggrian.tools.defaults.Defaults
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
@LargeTest
class SharedPreferencesDefaultsTest {

    private lateinit var context: Context
    private lateinit var testPreferences: SharedPreferences

    @Before
    fun createTest() {
        context = InstrumentationRegistry.getContext()
        testPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        testPreferences.edit().clear().commit()
    }

    @Test
    fun sharedPreferences() {
        val defaults = Defaults sharedPreferences testPreferences
        defaults {
            set("name", "Hendra")
            set("age", 25)
        }
        assertEquals("Hendra", defaults["name"])
        assertEquals(25, defaults.getInt("age"))
    }

    @Test
    fun context() {
        val defaults = Defaults sharedPreferences context
        defaults {
            set("name", "Hendra")
            set("age", 25)
        }
        assertEquals("Hendra", defaults["name"])
        assertEquals(25, defaults.getInt("age"))
    }
}