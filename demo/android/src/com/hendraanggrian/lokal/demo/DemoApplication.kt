package com.hendraanggrian.lokal.demo

import android.app.Application
import com.hendraanggrian.lokal.Android
import com.hendraanggrian.lokal.Lokal
import com.hendraanggrian.lokal.LokalDebugger

@Suppress("unused")
class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Lokal.setDebugger(LokalDebugger.Android)
    }
}