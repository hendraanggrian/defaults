package com.hendraanggrian.local.demo

import com.hendraanggrian.local.Local

class DemoApplication {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoApplication()
        }
    }

    @Local lateinit var name: String
    @Local @JvmField var age: Int = 0

    init {

    }
}