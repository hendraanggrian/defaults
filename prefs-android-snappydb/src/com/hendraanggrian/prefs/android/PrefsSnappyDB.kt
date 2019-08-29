@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.prefs.android

import com.hendraanggrian.prefs.EditablePrefs
import com.hendraanggrian.prefs.Prefs
import com.snappydb.DB

fun Prefs.Companion.of(source: DB): EditablePrefs =
    DBPrefs(source)

fun Prefs.Companion.safeOf(source: DB): EditablePrefs =
    SafeDBPrefs(source)

inline fun Prefs.Companion.bind(source: DB, target: Any) =
    bind(of(source), target)

inline fun Prefs.Companion.safeBind(source: DB, target: Any) =
    bind(safeOf(source), target)
