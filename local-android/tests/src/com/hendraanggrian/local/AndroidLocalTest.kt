package com.hendraanggrian.local

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
class AndroidLocalTest {

    private lateinit var testPreferences: SharedPreferences

    @Before
    fun createTest() {
        testPreferences =
            PreferenceManager.getDefaultSharedPreferences(InstrumentationRegistry.getContext())
        testPreferences.edit().clear().commit()
    }

    @Test
    fun test() {
        val preferences = Local sharedPreferences testPreferences
        preferences.edit {
            set("name", "Hendra")
            setInt("age", 25)
        }
        assertEquals("Hendra", preferences["name"])
        assertEquals(25, preferences.getInt("age"))
    }
}