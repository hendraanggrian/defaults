package com.hendraanggrian.lokal

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
class AndroidLokalTest {

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
        val lokal = testPreferences.toLokal()
        lokal.invoke {
            it["name"] = "Hendra"
            it["age"] = 25
        }
        assertEquals("Hendra", lokal["name"])
        assertEquals(25, lokal.getInt("age"))
    }

    @Test
    fun context() {
        val lokal = context.toLokal()
        lokal {
            it["name"] = "Hendra"
            it["age"] = 25
        }
        assertEquals("Hendra", lokal["name"])
        assertEquals(25, lokal.getInt("age"))
    }
}