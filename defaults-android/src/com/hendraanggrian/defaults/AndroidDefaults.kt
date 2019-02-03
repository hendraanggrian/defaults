@file:Suppress("NOTHING_TO_INLINE", "DEPRECATION")

package com.hendraanggrian.defaults

import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log

@PublishedApi internal val TAG = Defaults::class.java.simpleName

/** Android debugger, prints to [Log.DEBUG]. */
val DefaultsDebugger.Companion.Android: DefaultsDebugger
    get() = DefaultsDebugger { Log.d(TAG, it) }

/** Creates defaults instance from shared preferences. */
inline fun SharedPreferences.toDefaults(): SharedPreferencesDefaults =
    SharedPreferencesDefaults(this)

/** Creates defaults instance from shared preferences. */
inline fun SharedPreferences.bindDefaults(target: Any): DefaultsSaver =
    toDefaults().bindDefaults(target)

/** Creates defaults instance from default shared preferences in context. */
inline fun Context.toDefaults(): SharedPreferencesDefaults =
    PreferenceManager.getDefaultSharedPreferences(this).toDefaults()

/** Creates defaults instance from default shared preferences in context. */
inline fun Context.bindDefaults(target: Any): DefaultsSaver =
    toDefaults().bindDefaults(target)

/** Creates defaults instance from default shared preferences in context. */
inline fun Context.bindDefaults(): DefaultsSaver =
    toDefaults().bindDefaults(this)

/** Creates defaults instance from default shared preferences in fragment. */
inline fun Fragment.toDefaults(): SharedPreferencesDefaults =
    activity.toDefaults()

/** Creates defaults instance from default shared preferences in fragment. */
inline fun Fragment.bindDefaults(target: Any): DefaultsSaver =
    toDefaults().bindDefaults(target)

/** Creates defaults instance from default shared preferences in fragment. */
inline fun Fragment.bindDefaults(): DefaultsSaver =
    toDefaults().bindDefaults(this)

/** Creates defaults instance from default shared preferences in support fragment. */
inline fun androidx.fragment.app.Fragment.toDefaults(): SharedPreferencesDefaults =
    checkNotNull(context) { "Context is not yet attached to this fragment" }.toDefaults()

/** Creates defaults instance from default shared preferences in support fragment. */
inline fun androidx.fragment.app.Fragment.bindDefaults(target: Any): DefaultsSaver =
    toDefaults().bindDefaults(target)

/** Creates defaults instance from default shared preferences in support fragment. */
inline fun androidx.fragment.app.Fragment.bindDefaults(): DefaultsSaver =
    toDefaults().bindDefaults(this)