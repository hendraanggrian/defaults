package com.hendraanggrian.prefs.jvm

import com.hendraanggrian.prefs.Prefs
import com.hendraanggrian.prefs.ReadablePrefs
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class JvmPrefsTest {
    private lateinit var jvmPrefs: JvmPrefs

    @BeforeTest fun createTest() {
        Prefs.setLogger(Prefs.Logger.System)
        jvmPrefs = Prefs.userNode<ReadablePrefs>()
    }

    @Test fun jvm() {
        jvmPrefs["name"] = "Hendra"
        jvmPrefs.save()
        assertEquals("Hendra", jvmPrefs["name"])
    }
}