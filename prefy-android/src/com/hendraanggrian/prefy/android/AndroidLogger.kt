@file:JvmMultifileClass
@file:JvmName("PrefyAndroidKt")

package com.hendraanggrian.prefy.android

import android.util.Log
import com.hendraanggrian.prefy.PreferencesLogger

private const val TAG = "Prefy"

/** Logger that prints to [Log], matching its supported channels. */
val PreferencesLogger.Companion.Android: PreferencesLogger
    get() = object : PreferencesLogger {
        override fun info(message: String) {
            Log.i(TAG, message)
        }

        override fun warn(message: String) {
            Log.w(TAG, message)
        }
    }
