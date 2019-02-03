package com.hendraanggrian.defaults.demo

import android.app.Application
import com.hendraanggrian.defaults.Android
import com.hendraanggrian.defaults.Defaults
import com.hendraanggrian.defaults.DefaultsDebugger

@Suppress("unused")
class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Defaults.setDebugger(DefaultsDebugger.Android)
    }
}