@file:Suppress("NOTHING_TO_INLINE", "DEPRECATION")

package com.hendraanggrian.local

import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.hendraanggrian.local.android.LocalSharedPreferences

/** Android debugger, prints to [Log.DEBUG]. */
inline val LocalDebugger.Companion.Android: LocalDebugger
    get() = LocalDebugger { Log.d(ReadableLocal::class.java.simpleName, it) }

/** Creates local instance from shared preferences. */
inline fun SharedPreferences.toLocal(): LocalSharedPreferences =
    LocalSharedPreferences(this)

/** Creates local instance from shared preferences. */
inline fun SharedPreferences.bindLocal(target: Any): LocalSaver =
    toLocal().bindLocal(target)

/** Creates local instance from default shared preferences in context. */
inline fun Context.toLocal(): LocalSharedPreferences =
    PreferenceManager.getDefaultSharedPreferences(this).toLocal()

/** Creates local instance from default shared preferences in context. */
inline fun Context.bindLocal(target: Any = this): LocalSaver =
    toLocal().bindLocal(target)

/** Creates local instance from default shared preferences in fragment. */
inline fun Fragment.toLocal(): LocalSharedPreferences =
    activity.toLocal()

/** Creates local instance from default shared preferences in fragment. */
inline fun Fragment.bindLocal(target: Any = this): LocalSaver =
    toLocal().bindLocal(target)

/** Creates local instance from default shared preferences in support fragment. */
inline fun androidx.fragment.app.Fragment.toLocal(): LocalSharedPreferences =
    checkNotNull(context) { "Context is not yet attached to this fragment" }.toLocal()

/** Creates local instance from default shared preferences in support fragment. */
inline fun androidx.fragment.app.Fragment.bindLocal(target: Any = this): LocalSaver =
    toLocal().bindLocal(target)
