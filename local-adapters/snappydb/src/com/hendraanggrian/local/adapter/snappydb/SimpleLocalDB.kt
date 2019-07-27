package com.hendraanggrian.local.adapter.snappydb

import com.snappydb.DB

class SimpleLocalDB internal constructor(nativeDB: DB) : LocalDB(nativeDB) {

    override fun getByte(key: String): Byte? = get(key)?.toByte()

    override fun set(key: String, value: Byte) = set(key, value.toString())
}