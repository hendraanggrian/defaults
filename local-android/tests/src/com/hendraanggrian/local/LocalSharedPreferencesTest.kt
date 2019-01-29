package com.hendraanggrian.local

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.test.InstrumentationRegistry
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
@LargeTest
class LocalSharedPreferencesTest {

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
        val preferences = Local sharedPreferences testPreferences
        preferences.edit {
            set("name", "Hendra")
            set("age", 25)
        }
        assertEquals("Hendra", preferences["name"])
        assertEquals(25, preferences.getInt("age"))
    }

    @Test
    fun context() {
        val preferences = Local sharedPreferences context
        preferences.edit {
            set("name", "Hendra")
            set("age", 25)
        }
        assertEquals("Hendra", preferences["name"])
        assertEquals(25, preferences.getInt("age"))
    }
}