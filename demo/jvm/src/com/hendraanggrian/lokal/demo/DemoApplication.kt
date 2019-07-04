package com.hendraanggrian.lokal.demo

import com.hendraanggrian.lokal.BindLokal
import com.hendraanggrian.lokal.Lokal
import com.hendraanggrian.lokal.bindLokal
import org.apache.commons.lang3.SystemUtils
import java.io.File

class DemoApplication {

    companion object {
        @JvmStatic
        fun main(@Suppress("UnusedMainParameter") args: Array<String>) {
            Lokal.setDebugger(Lokal.Debugger.System)
            DemoApplication()
        }
    }

    @BindLokal("first_name") @JvmField var firstName: String
    @BindLokal("last_name") @JvmField var lastName: String

    init {
        val file = File(SystemUtils.USER_HOME, "Desktop").resolve("test.properties")
        val saver = file.bindLokal(this)
        firstName = "Hendra"
        lastName = "Anggrian"
        saver.saveAsync()
    }
}