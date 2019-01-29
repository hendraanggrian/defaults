@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.defaults

import android.util.Log

inline val DefaultsDebugger.Companion.Android: DefaultsDebugger
    get() = DefaultsDebugger { Log.d(Defaults.TAG, it) }