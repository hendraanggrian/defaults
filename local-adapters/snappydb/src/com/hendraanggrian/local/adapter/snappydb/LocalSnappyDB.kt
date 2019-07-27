@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.hendraanggrian.local.adapter.snappydb

import com.hendraanggrian.local.Local
import com.snappydb.DB

fun Local.of(
    source: DB,
    useSimple: Boolean = false
) = when {
    useSimple -> SimpleLocalDB(source)
    else -> LocalDB(source)
}