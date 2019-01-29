package com.hendraanggrian.local.demo

import com.hendraanggrian.local.BindLocal
import com.hendraanggrian.local.Local
import com.hendraanggrian.local.bindLocal
import com.hendraanggrian.local.file
import org.apache.commons.lang3.SystemUtils
import java.io.File

class DemoApplication {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoApplication()
        }
    }

    @BindLocal @JvmField var name: String
    @BindLocal @JvmField var age: Int = 0

    init {
        val file = File(SystemUtils.USER_HOME, "Desktop").resolve("test.properties")
        val saver = bindLocal(Local file file)
        name = "Hendra Anggrian"
        age = 25
        saver.saveAsync()
    }
}