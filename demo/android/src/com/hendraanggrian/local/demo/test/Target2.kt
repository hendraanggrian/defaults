package com.hendraanggrian.local.demo.test

import com.hendraanggrian.local.BindLocal

class Target2 : Target1() {
    @BindLocal
    lateinit var test2: String
}