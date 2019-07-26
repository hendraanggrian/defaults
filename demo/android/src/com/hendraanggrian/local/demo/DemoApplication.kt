package com.hendraanggrian.local.demo

import android.app.Application
import com.hendraanggrian.local.Local
import com.hendraanggrian.local.android.Android
import com.hendraanggrian.local.LocalDebugger
import com.hendraanggrian.local.ReadableLocal

@Suppress("unused")
class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Local.setDebugger(LocalDebugger.Android)
    }
}