package com.hendraanggrian.prefs.android

import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.hendraanggrian.prefs.Prefs
import com.hendraanggrian.prefs.PrefsSaver
import com.hendraanggrian.prefs.internal.PrefsInternal

/** Activates/deactivates debugging mode, which prints messages about preferences binding. */
fun Prefs.Companion.setDebug(debug: Boolean) =
    PrefsInternal.setDebugInternal(debug) { Log.d(Prefs::class.java.simpleName, it) }

fun Prefs.Companion.of(source: SharedPreferences): SharedPrefs =
    SharedPrefs(source)

fun Prefs.Companion.of(source: Context): SharedPrefs =
    of(PreferenceManager.getDefaultSharedPreferences(source))

fun Prefs.Companion.of(source: Fragment): SharedPrefs =
    of(source.activity)

fun Prefs.Companion.of(source: androidx.fragment.app.Fragment): SharedPrefs =
    of(checkNotNull(source.context) { "Context is not yet attached to this fragment" })

fun Prefs.Companion.safeOf(source: SharedPreferences): SharedPrefs =
    SafeSharedPrefs(source)

fun Prefs.Companion.safeOf(source: Context): SharedPrefs =
    safeOf(PreferenceManager.getDefaultSharedPreferences(source))

fun Prefs.Companion.safeOf(source: Fragment): SharedPrefs =
    safeOf(source.activity)

fun Prefs.Companion.safeOf(source: androidx.fragment.app.Fragment): SharedPrefs =
    safeOf(checkNotNull(source.context) { "Context is not yet attached to this fragment" })

fun Prefs.Companion.bind(source: SharedPreferences, target: Any): PrefsSaver =
    of(source).bind(target)

fun Prefs.Companion.bind(source: Context, target: Any = source): PrefsSaver =
    of(source).bind(target)

fun Prefs.Companion.bind(source: Fragment, target: Any = this): PrefsSaver =
    of(source).bind(target)

fun Prefs.Companion.bind(
    source: androidx.fragment.app.Fragment,
    target: Any = source
): PrefsSaver =
    of(source).bind(target)

fun Prefs.Companion.safeBind(source: SharedPreferences, target: Any): PrefsSaver =
    safeOf(source).bind(target)

fun Prefs.Companion.safeBind(source: Context, target: Any = source): PrefsSaver =
    safeOf(source).bind(target)

fun Prefs.Companion.safeBind(source: Fragment, target: Any = this): PrefsSaver =
    safeOf(source).bind(target)

fun Prefs.Companion.safeBind(
    source: androidx.fragment.app.Fragment,
    target: Any = source
): PrefsSaver =
    safeOf(source).bind(target)
