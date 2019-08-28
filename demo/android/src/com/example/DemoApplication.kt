package com.example

import android.app.Application
import local.Local
import local.android.setDebug

@Suppress("unused")
class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Local.setDebug(true)
    }
}