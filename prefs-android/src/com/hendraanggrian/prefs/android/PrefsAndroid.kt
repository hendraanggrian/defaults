@file:JvmMultifileClass
@file:JvmName("PrefsAndroidKt")
@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.prefs.android

import android.util.Log
import com.hendraanggrian.prefs.Prefs

/** Logger that prints to [Log], matching its supported channels. */
val Prefs.Logger.Companion.Android: Prefs.Logger
    get() = object : Prefs.Logger {
        override fun info(message: String) {
            Log.i(Prefs::class.java.simpleName, message)
        }

        override fun warn(message: String) {
            Log.e(Prefs::class.java.simpleName, message)
        }
    }
