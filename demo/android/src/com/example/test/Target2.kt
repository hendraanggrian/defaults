package com.example.test

import local.BindLocal

class Target2 : Target1() {
    @BindLocal lateinit var test2: String
}