@file:Suppress("unused", "NOTHING_TO_INLINE")

package local.jvm

import java.io.File
import java.util.prefs.Preferences
import local.EditableLocal
import local.Local
import local.LocalSaver
import local.internal.LocalInternal

fun Local.Companion.setDebug(debug: Boolean) =
    LocalInternal.setDebugInternal(debug) { println(it) }

fun Local.Companion.of(source: File): EditableLocal =
    LocalProperties(source)

fun Local.Companion.of(source: Preferences): EditableLocal =
    LocalPreferences(source)

fun Local.Companion.safeOf(source: File): EditableLocal =
    SafeLocalProperties(source)

fun Local.Companion.safeOf(source: Preferences): EditableLocal =
    SafeLocalPreferences(source)

inline fun Local.Companion.bind(source: File, target: Any): LocalSaver =
    bind(of(source), target)

inline fun Local.Companion.bind(source: Preferences, target: Any): LocalSaver =
    bind(of(source), target)

inline fun Local.Companion.safeBind(source: File, target: Any): LocalSaver =
    bind(safeOf(source), target)

inline fun Local.Companion.safeBind(source: Preferences, target: Any): LocalSaver =
    bind(safeOf(source), target)
