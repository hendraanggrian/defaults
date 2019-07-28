package local.demo.test

import local.BindLocal

class Target2 : Target1() {
    @BindLocal lateinit var test2: String
}