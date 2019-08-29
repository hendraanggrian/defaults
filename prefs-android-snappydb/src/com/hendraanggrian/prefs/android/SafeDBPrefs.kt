package com.hendraanggrian.prefs.android

import com.snappydb.DB

internal class SafeDBPrefs(nativeDB: DB) : DBPrefs(nativeDB) {

    override fun getByte(key: String): Byte? = get(key)?.toByte()

    override fun set(key: String, value: Byte) = set(key, value.toString())
}
