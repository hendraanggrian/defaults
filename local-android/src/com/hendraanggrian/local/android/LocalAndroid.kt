@file:Suppress("DEPRECATION")

package com.hendraanggrian.local.android

import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.hendraanggrian.local.Local
import com.hendraanggrian.local.LocalDebugger
import com.hendraanggrian.local.LocalSaver
import com.hendraanggrian.local.ReadableLocal

/** Android debugger, prints to [Log.DEBUG]. */
inline val LocalDebugger.Companion.Android: LocalDebugger
    get() = LocalDebugger {
        Log.d(
            ReadableLocal::class.java.simpleName,
            it
        )
    }

fun Local.bind(source: SharedPreferences, target: Any): LocalSaver =
    bind(LocalSharedPreferences(source), target)

fun Local.bind(source: Context, target: Any = source): LocalSaver =
    bind(PreferenceManager.getDefaultSharedPreferences(source), target)

fun Local.bind(source: Fragment, target: Any = this): LocalSaver =
    bind(source.activity, target)

fun Local.bind(source: androidx.fragment.app.Fragment, target: Any = source): LocalSaver =
    bind(checkNotNull(source.activity) { "Context is not yet attached to this fragment" }, target)
