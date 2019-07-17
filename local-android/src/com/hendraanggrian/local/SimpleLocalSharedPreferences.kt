package com.hendraanggrian.local

import android.content.SharedPreferences

class SimpleLocalSharedPreferences(nativePreferences: SharedPreferences) :
    LocalSharedPreferences(nativePreferences) {

    override fun getDouble(key: String): Double? = get(key)?.toDouble()

    override fun getShort(key: String): Short? = get(key)?.toShort()

    override fun getByte(key: String): Byte? = get(key)?.toByte()

    override val editor: Local.Editor get() = Editor(nativePreferences.edit())

    class Editor(nativeEditor: SharedPreferences.Editor) :
        LocalSharedPreferences.Editor(nativeEditor) {

        override fun set(key: String, value: Double) = set(key, value.toString())

        override fun set(key: String, value: Short) = set(key, value.toString())

        override fun set(key: String, value: Byte) = set(key, value.toString())
    }
}