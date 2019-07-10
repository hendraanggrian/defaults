package com.hendraanggrian.local.demo

import android.app.Application
import com.hendraanggrian.local.Android
import com.hendraanggrian.local.Local

@Suppress("unused")
class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Local.setDebugger(Local.Debugger.Android)
    }
}