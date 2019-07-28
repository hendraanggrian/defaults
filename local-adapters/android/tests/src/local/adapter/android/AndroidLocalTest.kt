package local.adapter.android

import androidx.test.InstrumentationRegistry
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import local.Local
import local.WritableLocal
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertNull

@RunWith(AndroidJUnit4::class)
@LargeTest
class AndroidLocalTest {

    private lateinit var local: WritableLocal

    @Before
    fun createTest() {
        val local = Local.of(InstrumentationRegistry.getContext())
        local.writer { clear() }
    }

    @Test
    fun sharedPreferences() {
        assertNull(local["name"])
        assertNull(local.getInt("age"))
        local.writer {
            this["name"] = "Hendra"
            this["age"] = 25
        }
        assertEquals("Hendra", local["name"])
        assertEquals(25, local.getInt("age"))
    }
}