package com.example

import android.app.Application
import com.hendraanggrian.prefs.Prefs
import com.hendraanggrian.prefs.android.setDebug

@Suppress("unused")
class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Prefs.setDebug(true)
    }
}