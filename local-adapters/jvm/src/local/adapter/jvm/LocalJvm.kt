@file:Suppress("unused", "NOTHING_TO_INLINE")

package local.adapter.jvm

import java.io.File
import java.util.prefs.Preferences
import local.EditableLocal
import local.Local
import local.LocalSaver

fun Local.Companion.of(
    source: File,
    useSimple: Boolean = false
): EditableLocal = when {
    useSimple -> SimpleLocalProperties(source)
    else -> LocalProperties(source)
}

fun Local.Companion.of(
    source: Preferences,
    useSimple: Boolean = false
): EditableLocal = when {
    useSimple -> SimpleLocalPreferences(source)
    else -> LocalPreferences(source)
}

inline fun Local.Companion.bind(
    source: File,
    target: Any,
    useSimple: Boolean = false
): LocalSaver = bind(of(source, useSimple), target)

inline fun Local.Companion.bind(
    source: Preferences,
    target: Any,
    useSimple: Boolean = false
): LocalSaver = bind(of(source, useSimple), target)
