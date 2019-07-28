@file:Suppress("unused", "NOTHING_TO_INLINE")

package local.adapter.jvm

import java.io.File
import java.util.prefs.Preferences
import local.Local
import local.LocalSaver

fun Local.of(
    source: File,
    useSimple: Boolean = false
): LocalProperties = when {
    useSimple -> SimpleLocalProperties(source)
    else -> LocalProperties(source)
}

fun Local.of(
    source: Preferences,
    useSimple: Boolean = false
): LocalPreferences = when {
    useSimple -> SimpleLocalPreferences(source)
    else -> LocalPreferences(source)
}

inline fun Local.bind(
    source: File,
    target: Any,
    useSimple: Boolean = false
): LocalSaver = bind(of(source, useSimple), target)

inline fun Local.bind(
    source: Preferences,
    target: Any,
    useSimple: Boolean = false
): LocalSaver = bind(of(source, useSimple), target)
