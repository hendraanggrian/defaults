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
class AndroidLocalTest {

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
        val local = testPreferences.toLocal()
        local.editor {
            this["name"] = "Hendra"
            this["age"] = 25
        }
        assertEquals("Hendra", local["name"])
        assertEquals(25, local.getInt("age"))
    }

    @Test
    fun context() {
        val local = context.toLocal()
        local.editor {
            this["name"] = "Hendra"
            this["age"] = 25
        }
        assertEquals("Hendra", local["name"])
        assertEquals(25, local.getInt("age"))
    }
}