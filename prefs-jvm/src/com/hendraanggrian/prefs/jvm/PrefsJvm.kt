@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.prefs.jvm

import com.hendraanggrian.prefs.EditablePrefs
import com.hendraanggrian.prefs.Prefs
import com.hendraanggrian.prefs.PrefsSaver
import com.hendraanggrian.prefs.internal.PrefsInternal
import java.io.File
import java.util.prefs.Preferences

/** Activates/deactivates debugging mode, which prints messages about preferences binding. */
fun Prefs.Companion.setDebug(debug: Boolean) =
    PrefsInternal.setDebugInternal(debug) { println(it) }

fun Prefs.Companion.of(source: File): EditablePrefs =
    PropertiesPrefs(source)

fun Prefs.Companion.safeOf(source: File): EditablePrefs =
    SafePropertiesPrefs(source)

fun Prefs.Companion.of(source: Preferences): EditablePrefs =
    Prefs2(source)

fun Prefs.Companion.safeOf(source: Preferences): EditablePrefs =
    SafePrefs2(source)

inline fun Prefs.Companion.bind(source: File, target: Any): PrefsSaver =
    bind(of(source), target)

inline fun Prefs.Companion.safeBind(source: File, target: Any): PrefsSaver =
    bind(safeOf(source), target)

inline fun Prefs.Companion.bind(source: Preferences, target: Any): PrefsSaver =
    bind(of(source), target)

inline fun Prefs.Companion.safeBind(source: Preferences, target: Any): PrefsSaver =
    bind(safeOf(source), target)
