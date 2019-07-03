@file:Suppress("NOTHING_TO_INLINE", "DEPRECATION")

package com.hendraanggrian.lokal

import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log

@PublishedApi internal val TAG = Lokal::class.java.simpleName

/** Android debugger, prints to [Log.DEBUG]. */
val LokalDebugger.Companion.Android: LokalDebugger
    get() = LokalDebugger { Log.d(TAG, it) }

/** Creates lokal instance from shared preferences. */
inline fun SharedPreferences.toLokal(): SharedPreferencesLokal =
    SharedPreferencesLokal(this)

/** Creates lokal instance from shared preferences. */
inline fun SharedPreferences.bindLokal(target: Any): LokalSaver =
    toLokal().bindLokal(target)

/** Creates lokal instance from default shared preferences in context. */
inline fun Context.toLokal(): SharedPreferencesLokal =
    PreferenceManager.getDefaultSharedPreferences(this).toLokal()

/** Creates lokal instance from default shared preferences in context. */
inline fun Context.bindLokal(target: Any = this): LokalSaver =
    toLokal().bindLokal(target)

/** Creates lokal instance from default shared preferences in fragment. */
inline fun Fragment.toLokal(): SharedPreferencesLokal =
    activity.toLokal()

/** Creates lokal instance from default shared preferences in fragment. */
inline fun Fragment.bindLokal(target: Any = this): LokalSaver =
    toLokal().bindLokal(target)

/** Creates lokal instance from default shared preferences in support fragment. */
inline fun androidx.fragment.app.Fragment.toLokal(): SharedPreferencesLokal =
    checkNotNull(context) { "Context is not yet attached to this fragment" }.toLokal()

/** Creates lokal instance from default shared preferences in support fragment. */
inline fun androidx.fragment.app.Fragment.bindLokal(target: Any = this): LokalSaver =
    toLokal().bindLokal(target)