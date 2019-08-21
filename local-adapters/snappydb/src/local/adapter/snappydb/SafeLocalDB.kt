package local.adapter.snappydb

import com.snappydb.DB

class SafeLocalDB internal constructor(nativeDB: DB) : LocalDB(nativeDB) {

    override fun getByte(key: String): Byte? = get(key)?.toByte()

    override fun set(key: String, value: Byte) = set(key, value.toString())
}
