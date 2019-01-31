package com.hendraanggrian.defaults.demo

import com.hendraanggrian.defaults.BindDefault
import com.hendraanggrian.defaults.Defaults
import com.hendraanggrian.defaults.DefaultsDebugger
import com.hendraanggrian.defaults.bindDefaults
import com.hendraanggrian.defaults.get
import org.apache.commons.lang3.SystemUtils
import java.io.File

class DemoApplication {

    companion object {

        @JvmStatic
        fun main(@Suppress("UnusedMainParameter") args: Array<String>) {
            Defaults.setDebug(DefaultsDebugger.Default)
            DemoApplication()
        }
    }

    @BindDefault @JvmField var name: String
    @BindDefault @JvmField var age: Int = 0

    init {
        val file = File(SystemUtils.USER_HOME, "Desktop").resolve("test.properties")
        val saver = bindDefaults(Defaults[file])
        name = "Hendra Anggrian"
        age = 25
        saver.saveAsync()
    }
}