@file:JvmMultifileClass
@file:JvmName("PrefsAndroidKt")
@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.prefs.android

import android.util.Log
import com.hendraanggrian.prefs.Prefs
import com.hendraanggrian.prefs.internal.PrefsInternal

/**
 * Activates/deactivates debugging messages about preferences binding.
 * The messages will be printed in Android [Log.d].
 */
fun Prefs.Companion.setDebug(debug: Boolean) =
    PrefsInternal.setDebugInternal(debug) { Log.d(Prefs::class.java.simpleName, it) }
