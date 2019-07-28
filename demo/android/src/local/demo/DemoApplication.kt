package local.demo

import android.app.Application
import local.Local
import local.LocalLogger
import local.adapter.android.Android

@Suppress("unused")
class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Local.setDebugger(LocalLogger.Android)
    }
}