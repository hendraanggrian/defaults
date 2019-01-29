package com.hendraanggrian.defaults.demo

import com.hendraanggrian.defaults.Default
import com.hendraanggrian.defaults.Defaults
import com.hendraanggrian.defaults.DefaultsDebugger
import com.hendraanggrian.defaults.bindDefaults
import com.hendraanggrian.defaults.from
import org.apache.commons.lang3.SystemUtils
import java.io.File

class DemoApplication {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            Defaults.setDebug(DefaultsDebugger.Default)
            DemoApplication()
        }
    }

    @Default @JvmField var name: String
    @Default @JvmField var age: Int = 0

    init {
        val file = File(SystemUtils.USER_HOME, "Desktop").resolve("test.properties")
        val saver = bindDefaults(Defaults.from(file))
        name = "Hendra Anggrian"
        age = 25
        saver.saveAsync()
    }
}