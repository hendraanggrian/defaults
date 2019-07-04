@file:Suppress("NOTHING_TO_INLINE", "DEPRECATION")

package com.hendraanggrian.lokal

import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log

/** Android debugger, prints to [Log.DEBUG]. */
inline val Lokal.Debugger.Companion.Android: Lokal.Debugger
    get() = Lokal.Debugger { Log.d(Lokal::class.java.simpleName, it) }

/** Creates lokal instance from shared preferences. */
inline fun SharedPreferences.toLokal(): LokalSharedPreferences =
    LokalSharedPreferences(this)

/** Creates lokal instance from shared preferences. */
inline fun SharedPreferences.bindLokal(target: Any): Lokal.Saver =
    toLokal().bindLokal(target)

/** Creates lokal instance from default shared preferences in context. */
inline fun Context.toLokal(): LokalSharedPreferences =
    PreferenceManager.getDefaultSharedPreferences(this).toLokal()

/** Creates lokal instance from default shared preferences in context. */
inline fun Context.bindLokal(target: Any = this): Lokal.Saver =
    toLokal().bindLokal(target)

/** Creates lokal instance from default shared preferences in fragment. */
inline fun Fragment.toLokal(): LokalSharedPreferences =
    activity.toLokal()

/** Creates lokal instance from default shared preferences in fragment. */
inline fun Fragment.bindLokal(target: Any = this): Lokal.Saver =
    toLokal().bindLokal(target)

/** Creates lokal instance from default shared preferences in support fragment. */
inline fun androidx.fragment.app.Fragment.toLokal(): LokalSharedPreferences =
    checkNotNull(context) { "Context is not yet attached to this fragment" }.toLokal()

/** Creates lokal instance from default shared preferences in support fragment. */
inline fun androidx.fragment.app.Fragment.bindLokal(target: Any = this): Lokal.Saver =
    toLokal().bindLokal(target)