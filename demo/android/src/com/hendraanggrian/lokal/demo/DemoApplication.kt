package com.hendraanggrian.lokal.demo

import android.app.Application
import com.hendraanggrian.lokal.Android
import com.hendraanggrian.lokal.Lokal

@Suppress("unused")
class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Lokal.setDebugger(Lokal.Debugger.Android)
    }
}