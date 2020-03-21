package com.example.prefs

import android.app.Application
import com.hendraanggrian.prefs.Prefs
import com.hendraanggrian.prefs.android.Android

class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Prefs.setLogger(Prefs.Logger.Android)
    }
}