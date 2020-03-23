@file:JvmMultifileClass
@file:JvmName("PrefsAndroidKt")
@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.prefs.android

import android.util.Log
import com.hendraanggrian.prefs.Prefs

private val TAG: String = Prefs::class.java.simpleName

/** Logger that prints to [Log], matching its supported channels. */
val Prefs.Logger.Companion.Android: Prefs.Logger
    get() = object : Prefs.Logger {
        override fun info(message: String) {
            Log.i(TAG, message)
        }

        override fun warn(message: String) {
            Log.w(TAG, message)
        }
    }
