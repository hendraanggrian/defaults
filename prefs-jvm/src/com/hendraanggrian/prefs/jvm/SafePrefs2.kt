package com.hendraanggrian.prefs.jvm

import java.util.prefs.Preferences

internal class SafePrefs2 internal constructor(nativePreferences: Preferences) :
    Prefs2(nativePreferences) {

    override fun getShort(key: String): Short? = get(key)?.toShort()

    override fun getByte(key: String): Byte? = get(key)?.toByte()

    override fun set(key: String, value: Short) = set(key, value.toString())

    override fun set(key: String, value: Byte) = set(key, value.toString())
}
