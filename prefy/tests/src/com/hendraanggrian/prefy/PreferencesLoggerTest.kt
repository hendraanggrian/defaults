package com.hendraanggrian.prefy

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class PreferencesLoggerTest {

    @Test fun setLogger() {
        val infos = mutableListOf<String>()
        val warns = mutableListOf<String>()
        Prefy.setLogger(object : PreferencesLogger {
            override fun info(message: String) {
                infos += message
            }

            override fun warn(message: String) {
                warns += message
            }
        })
        Prefy.info("A")
        Prefy.info("B")
        Prefy.warn("C")
        assertThat(infos).containsExactly("A", "B")
        assertThat(warns).containsExactly("C")
    }
}