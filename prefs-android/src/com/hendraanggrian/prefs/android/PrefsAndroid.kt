@file:Suppress("NOTHING_TO_INLINE", "DEPRECATION")

package com.hendraanggrian.prefs.android

import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.hendraanggrian.prefs.Prefs
import com.hendraanggrian.prefs.PrefsSaver
import com.hendraanggrian.prefs.WritablePrefs
import com.hendraanggrian.prefs.internal.PrefsInternal

/** Activates/deactivates debugging mode, which prints messages about preferences binding. */
fun Prefs.Companion.setDebug(debug: Boolean) =
    PrefsInternal.setDebugInternal(debug) { Log.d(Prefs::class.java.simpleName, it) }

fun Prefs.Companion.of(source: SharedPreferences): WritablePrefs =
    SharedPrefs(source)

inline fun Prefs.Companion.of(source: Context): WritablePrefs =
    of(PreferenceManager.getDefaultSharedPreferences(source))

inline fun Prefs.Companion.of(source: Fragment): WritablePrefs =
    of(source.activity)

inline fun Prefs.Companion.of(source: androidx.fragment.app.Fragment): WritablePrefs =
    of(checkNotNull(source.context) { "Context is not yet attached to this fragment" })

fun Prefs.Companion.safeOf(source: SharedPreferences): WritablePrefs =
    SafeSharedPrefs(source)

inline fun Prefs.Companion.safeOf(source: Context): WritablePrefs =
    safeOf(PreferenceManager.getDefaultSharedPreferences(source))

inline fun Prefs.Companion.safeOf(source: Fragment): WritablePrefs =
    safeOf(source.activity)

inline fun Prefs.Companion.safeOf(source: androidx.fragment.app.Fragment): WritablePrefs =
    safeOf(checkNotNull(source.context) { "Context is not yet attached to this fragment" })

inline fun Prefs.Companion.bind(source: SharedPreferences, target: Any): PrefsSaver =
    bind(of(source), target)

inline fun Prefs.Companion.bind(source: Context, target: Any = source): PrefsSaver =
    bind(of(source), target)

inline fun Prefs.Companion.bind(source: Fragment, target: Any = this): PrefsSaver =
    bind(of(source), target)

inline fun Prefs.Companion.bind(
    source: androidx.fragment.app.Fragment,
    target: Any = source
): PrefsSaver =
    bind(of(source), target)

inline fun Prefs.Companion.safeBind(source: SharedPreferences, target: Any): PrefsSaver =
    bind(safeOf(source), target)

inline fun Prefs.Companion.safeBind(source: Context, target: Any = source): PrefsSaver =
    bind(safeOf(source), target)

inline fun Prefs.Companion.safeBind(source: Fragment, target: Any = this): PrefsSaver =
    bind(safeOf(source), target)

inline fun Prefs.Companion.safeBind(
    source: androidx.fragment.app.Fragment,
    target: Any = source
): PrefsSaver =
    bind(safeOf(source), target)
