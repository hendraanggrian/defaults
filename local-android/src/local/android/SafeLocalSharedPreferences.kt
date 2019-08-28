package local.android

import android.content.SharedPreferences
import local.Local
import local.LocalEditor

internal class SafeLocalSharedPreferences internal constructor(nativePreferences: SharedPreferences) :
    LocalSharedPreferences(nativePreferences) {

    override fun getDouble(key: String): Double? = get(key)?.toDouble()

    override fun getShort(key: String): Short? = get(key)?.toShort()

    override fun getByte(key: String): Byte? = get(key)?.toByte()

    override val editor: LocalEditor
        get() = Editor(this, nativePreferences.edit())

    class Editor(source: Local, nativeEditor: SharedPreferences.Editor) :
        LocalSharedPreferences.Editor(source, nativeEditor) {

        override fun set(key: String, value: Double) = set(key, value.toString())

        override fun set(key: String, value: Short) = set(key, value.toString())

        override fun set(key: String, value: Byte) = set(key, value.toString())
    }
}
