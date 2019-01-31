package com.hendraanggrian.defaults.demo.test

import com.hendraanggrian.defaults.BindDefault

class Target2 : Target1() {
    @BindDefault lateinit var test2: String
}