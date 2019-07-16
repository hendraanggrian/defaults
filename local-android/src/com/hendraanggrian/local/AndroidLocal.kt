@file:Suppress("NOTHING_TO_INLINE", "DEPRECATION")

package com.hendraanggrian.local

import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log

/** Android debugger, prints to [Log.DEBUG]. */
inline val Local.Debugger.Companion.Android: Local.Debugger
    get() = Local.Debugger { Log.d(Local::class.java.simpleName, it) }

/** Creates local instance from shared preferences. */
inline fun SharedPreferences.toLocal(): LocalSharedPreferences =
    LocalSharedPreferences(this)

/** Creates local instance from shared preferences. */
inline fun SharedPreferences.bindLocal(target: Any): Local.Saver =
    toLocal().bindLocal(target)

/** Creates local instance from default shared preferences in context. */
inline fun Context.toLocal(): LocalSharedPreferences =
    PreferenceManager.getDefaultSharedPreferences(this).toLocal()

/** Creates local instance from default shared preferences in context. */
inline fun Context.bindLocal(target: Any = this): Local.Saver =
    toLocal().bindLocal(target)

/** Creates local instance from default shared preferences in fragment. */
inline fun Fragment.toLocal(): LocalSharedPreferences =
    activity.toLocal()

/** Creates local instance from default shared preferences in fragment. */
inline fun Fragment.bindLocal(target: Any = this): Local.Saver =
    toLocal().bindLocal(target)

/** Creates local instance from default shared preferences in support fragment. */
inline fun androidx.fragment.app.Fragment.toLocal(): LocalSharedPreferences =
    checkNotNull(context) { "Context is not yet attached to this fragment" }.toLocal()

/** Creates local instance from default shared preferences in support fragment. */
inline fun androidx.fragment.app.Fragment.bindLocal(target: Any = this): Local.Saver =
    toLocal().bindLocal(target)
