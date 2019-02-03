package com.hendraanggrian.defaults

import android.util.Log

@PublishedApi internal val TAG = Defaults.TAG.substringAfterLast('.')

/** Android debugger, prints to [Log.DEBUG]. */
val DefaultsDebugger.Companion.Android: DefaultsDebugger
    get() = DefaultsDebugger { Log.d(TAG, it) }