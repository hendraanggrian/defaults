package com.hendraanggrian.local.adapter.android

import android.content.SharedPreferences
import com.hendraanggrian.local.LocalWriter
import com.hendraanggrian.local.ReadableLocal

class SimpleLocalSharedPreferences internal constructor(nativePreferences: SharedPreferences) :
    LocalSharedPreferences(nativePreferences) {

    override fun getDouble(key: String): Double? = get(key)?.toDouble()

    override fun getShort(key: String): Short? = get(key)?.toShort()

    override fun getByte(key: String): Byte? = get(key)?.toByte()

    override val writer: LocalWriter
        get() = Editor(this, nativePreferences.edit())

    class Editor(source: ReadableLocal, nativeEditor: SharedPreferences.Editor) :
        LocalSharedPreferences.Editor(source, nativeEditor) {

        override fun set(key: String, value: Double) = set(key, value.toString())

        override fun set(key: String, value: Short) = set(key, value.toString())

        override fun set(key: String, value: Byte) = set(key, value.toString())
    }
}
