package com.hendraanggrian.prefs.android

import android.content.SharedPreferences
import com.hendraanggrian.prefs.Prefs
import com.hendraanggrian.prefs.PrefsEditor

internal class SafeSharedPrefs(nativePreferences: SharedPreferences) :
    SharedPrefs(nativePreferences) {

    override fun getDouble(key: String): Double? = get(key)?.toDouble()

    override fun getShort(key: String): Short? = get(key)?.toShort()

    override fun getByte(key: String): Byte? = get(key)?.toByte()

    override val editor: PrefsEditor
        get() = Editor(this, nativePreferences.edit())

    class Editor(source: Prefs, nativeEditor: SharedPreferences.Editor) :
        SharedPrefs.Editor(source, nativeEditor) {

        override fun set(key: String, value: Double) = set(key, value.toString())

        override fun set(key: String, value: Short) = set(key, value.toString())

        override fun set(key: String, value: Byte) = set(key, value.toString())
    }
}
