package com.hendraanggrian.local.demo

import com.hendraanggrian.local.Local
import com.hendraanggrian.local.LocalSettings
import com.hendraanggrian.local.bind
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

    @Local @JvmField var name: String
    @Local @JvmField var age: Int = 0

    init {
        val file = File(SystemUtils.USER_HOME, "Desktop").resolve("test.properties")
        val saver = LocalSettings file file bind this
        name = "Hendra Anggrian"
        age = 25
        saver.saveAsync()
    }
}