package com.hendraanggrian.defaults.demo

import android.app.Application

@Suppress("unused")
class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Preferencer.setDebug(BuildConfig.DEBUG)
    }
}