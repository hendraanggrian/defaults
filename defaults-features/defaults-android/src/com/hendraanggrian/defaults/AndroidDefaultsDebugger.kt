@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.defaults

import android.util.Log

@PublishedApi internal const val TAG = "Defaults"

/** Android debugger, prints to [Log.DEBUG]. */
inline val DefaultsDebugger.Companion.Android: DefaultsDebugger
    get() = DefaultsDebugger { Log.d(TAG, it) }