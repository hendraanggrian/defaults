@file:Suppress("unused", "NOTHING_TO_INLINE", "DEPRECATION")

package com.hendraanggrian.local.adapter.android

import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.hendraanggrian.local.Local
import com.hendraanggrian.local.LocalLogger
import com.hendraanggrian.local.LocalSaver
import com.hendraanggrian.local.ReadableLocal
import com.hendraanggrian.local.WritableLocal

/** Android debugger, prints to [Log.DEBUG]. */
inline val LocalLogger.Companion.Android: LocalLogger
    get() = LocalLogger {
        Log.d(ReadableLocal::class.java.simpleName, it)
    }

fun Local.of(
    source: SharedPreferences,
    useSimple: Boolean = false
): LocalSharedPreferences = when {
    useSimple -> SimpleLocalSharedPreferences(source)
    else -> LocalSharedPreferences(source)
}

inline fun Local.of(
    source: Context,
    useSimple: Boolean = false
): WritableLocal = of(PreferenceManager.getDefaultSharedPreferences(source), useSimple)

inline fun Local.of(
    source: Fragment,
    useSimple: Boolean = false
): WritableLocal = of(source.activity, useSimple)

inline fun Local.of(
    source: androidx.fragment.app.Fragment,
    useSimple: Boolean = true
): WritableLocal =
    of(checkNotNull(source.context) { "Context is not yet attached to this fragment" }, useSimple)

inline fun Local.bind(
    source: SharedPreferences,
    target: Any,
    useSimple: Boolean = false
): LocalSaver = bind(of(source, useSimple), target)

inline fun Local.bind(
    source: Context,
    target: Any = source,
    useSimple: Boolean = false
): LocalSaver = bind(of(source, useSimple), target)

inline fun Local.bind(
    source: Fragment,
    target: Any = this,
    useSimple: Boolean = false
): LocalSaver = bind(of(source, useSimple), target)

inline fun Local.bind(
    source: androidx.fragment.app.Fragment,
    target: Any = source,
    useSimple: Boolean = false
): LocalSaver = bind(of(source, useSimple), target)
