@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.defaults

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log

@PublishedApi internal val TAG = Defaults.TAG.substringAfterLast('.')

/** Android debugger, prints to [Log.DEBUG]. */
inline val DefaultsDebugger.Companion.Android: DefaultsDebugger
    get() = DefaultsDebugger { Log.d(TAG, it) }

/** Creates defaults instance from shared preferences. */
inline fun Defaults.Companion.from(sharedPreferences: SharedPreferences): SharedPreferencesDefaults =
    SharedPreferencesDefaults(sharedPreferences)

/** Creates defaults instance from default shared preferences in context. */
inline fun Defaults.Companion.from(context: Context): SharedPreferencesDefaults =
    from(PreferenceManager.getDefaultSharedPreferences(context))