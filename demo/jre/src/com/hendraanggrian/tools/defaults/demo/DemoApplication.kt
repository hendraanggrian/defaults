package com.hendraanggrian.tools.defaults.demo

import com.hendraanggrian.tools.defaults.Default
import com.hendraanggrian.tools.defaults.Defaults
import com.hendraanggrian.tools.defaults.bindLocal
import com.hendraanggrian.tools.defaults.file
import org.apache.commons.lang3.SystemUtils
import java.io.File

class DemoApplication {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoApplication()
        }
    }

    @Default @JvmField var name: String
    @Default @JvmField var age: Int = 0

    init {
        val file = File(SystemUtils.USER_HOME, "Desktop").resolve("test.properties")
        val saver = bindLocal(Defaults file file)
        name = "Hendra Anggrian"
        age = 25
        saver.saveAsync()
    }
}