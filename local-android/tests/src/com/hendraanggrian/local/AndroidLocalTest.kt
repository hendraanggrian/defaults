package com.hendraanggrian.local

import androidx.test.InstrumentationRegistry
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.hendraanggrian.local.android.of
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertNull

@RunWith(AndroidJUnit4::class)
@LargeTest
class AndroidLocalTest {

    private lateinit var local: EditableLocal

    @Before
    fun createTest() {
        val local = Local.of(InstrumentationRegistry.getContext())
        local.editor { clear() }
    }

    @Test
    fun sharedPreferences() {
        assertNull(local["name"])
        assertNull(local.getInt("age"))
        local.editor {
            this["name"] = "Hendra"
            this["age"] = 25
        }
        assertEquals("Hendra", local["name"])
        assertEquals(25, local.getInt("age"))
    }
}