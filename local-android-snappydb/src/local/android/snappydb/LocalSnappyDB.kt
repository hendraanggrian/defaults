@file:Suppress("unused", "NOTHING_TO_INLINE")

package local.android.snappydb

import com.snappydb.DB
import local.EditableLocal
import local.Local

fun Local.Companion.of(source: DB): EditableLocal =
    LocalDB(source)

fun Local.Companion.safeOf(source: DB): EditableLocal =
    SafeLocalDB(source)

fun Local.Companion.bind(source: DB, target: Any) =
    bind(of(source), target)

fun Local.Companion.safeBind(source: DB, target: Any) =
    bind(safeOf(source), target)
