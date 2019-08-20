@file:Suppress("unused", "NOTHING_TO_INLINE")

package local.adapter.snappydb

import com.snappydb.DB
import local.Local

fun Local.Companion.of(
    source: DB,
    useSimple: Boolean = false
) = when {
    useSimple -> SimpleLocalDB(source)
    else -> LocalDB(source)
}
