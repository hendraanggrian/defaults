@file:Suppress("unused", "NOTHING_TO_INLINE", "DEPRECATION")

package local.adapter.android

import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import local.Local
import local.LocalSaver
import local.WritableLocal
import local.internal.LocalInternal

fun Local.Companion.setDebug(debug: Boolean) =
    LocalInternal.setDebugInternal(debug) { Log.d(Local::class.java.simpleName, it) }

fun Local.Companion.of(source: SharedPreferences): WritableLocal =
    LocalSharedPreferences(source)

inline fun Local.Companion.of(source: Context): WritableLocal =
    of(PreferenceManager.getDefaultSharedPreferences(source))

inline fun Local.Companion.of(source: Fragment): WritableLocal =
    of(source.activity)

inline fun Local.Companion.of(source: androidx.fragment.app.Fragment): WritableLocal =
    of(checkNotNull(source.context) { "Context is not yet attached to this fragment" })

fun Local.Companion.safeOf(source: SharedPreferences): WritableLocal =
    SafeLocalSharedPreferences(source)

inline fun Local.Companion.safeOf(source: Context): WritableLocal =
    safeOf(PreferenceManager.getDefaultSharedPreferences(source))

inline fun Local.Companion.safeOf(source: Fragment): WritableLocal =
    safeOf(source.activity)

inline fun Local.Companion.safeOf(source: androidx.fragment.app.Fragment): WritableLocal =
    safeOf(checkNotNull(source.context) { "Context is not yet attached to this fragment" })

inline fun Local.Companion.bind(source: SharedPreferences, target: Any): LocalSaver =
    bind(of(source), target)

inline fun Local.Companion.bind(source: Context, target: Any = source): LocalSaver =
    bind(of(source), target)

inline fun Local.Companion.bind(source: Fragment, target: Any = this): LocalSaver =
    bind(of(source), target)

inline fun Local.Companion.bind(
    source: androidx.fragment.app.Fragment,
    target: Any = source
): LocalSaver =
    bind(of(source), target)

inline fun Local.Companion.safeBind(source: SharedPreferences, target: Any): LocalSaver =
    bind(safeOf(source), target)

inline fun Local.Companion.safeBind(source: Context, target: Any = source): LocalSaver =
    bind(safeOf(source), target)

inline fun Local.Companion.safeBind(source: Fragment, target: Any = this): LocalSaver =
    bind(safeOf(source), target)

inline fun Local.Companion.safeBind(
    source: androidx.fragment.app.Fragment,
    target: Any = source
): LocalSaver =
    bind(safeOf(source), target)
