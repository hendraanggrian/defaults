package com.hendraanggrian.lokal.demo.test

import com.hendraanggrian.lokal.BindLokal

class Target2 : Target1() {
    @BindLokal
    lateinit var test2: String
}