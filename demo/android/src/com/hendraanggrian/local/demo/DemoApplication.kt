package com.hendraanggrian.local.demo

import android.app.Application
import com.hendraanggrian.local.Android
import com.hendraanggrian.local.LocalDebugger
import com.hendraanggrian.local.ReadableLocal

@Suppress("unused")
class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ReadableLocal.setDebugger(LocalDebugger.Android)
    }
}