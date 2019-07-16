package com.hendraanggrian.local

import android.content.SharedPreferences
import com.hendraanggrian.local.internal.ByteLocalEditorSupport
import com.hendraanggrian.local.internal.ByteLocalSupport
import com.hendraanggrian.local.internal.DoubleLocalEditorSupport
import com.hendraanggrian.local.internal.DoubleLocalSupport
import com.hendraanggrian.local.internal.ShortLocalEditorSupport
import com.hendraanggrian.local.internal.ShortLocalSupport

class LocalSharedPreferences(private val nativePreferences: SharedPreferences) : Local,
    DoubleLocalSupport,
    ShortLocalSupport,
    ByteLocalSupport {

    override fun contains(key: String): Boolean = key in nativePreferences

    override fun get(key: String): String? =
        nativePreferences.getString(key, null)

    override fun getOrDefault(key: String, defaultValue: String): String =
        nativePreferences.getString(key, defaultValue)!!

    override fun getBoolean(key: String): Boolean? =
        nativePreferences.getBoolean(key, false)

    override fun getBooleanOrDefault(key: String, defaultValue: Boolean): Boolean =
        nativePreferences.getBoolean(key, defaultValue)

    override fun getFloat(key: String): Float? =
        nativePreferences.getFloat(key, 0f)

    override fun getFloatOrDefault(key: String, defaultValue: Float): Float =
        nativePreferences.getFloat(key, defaultValue)

    override fun getLong(key: String): Long? =
        nativePreferences.getLong(key, 0L)

    override fun getLongOrDefault(key: String, defaultValue: Long): Long =
        nativePreferences.getLong(key, defaultValue)

    override fun getInt(key: String): Int =
        nativePreferences.getInt(key, 0)

    override fun getIntOrDefault(key: String, defaultValue: Int): Int =
        nativePreferences.getInt(key, defaultValue)

    override val editor: Local.Editor get() = Editor(nativePreferences.edit())

    fun toSharedPreferences(): SharedPreferences = nativePreferences

    private class Editor(private val nativeEditor: SharedPreferences.Editor) : Local.Editor,
        DoubleLocalEditorSupport,
        ShortLocalEditorSupport,
        ByteLocalEditorSupport {

        override fun remove(key: String) {
            nativeEditor.remove(key)
        }

        override fun clear() {
            nativeEditor.clear()
        }

        override fun set(key: String, value: String?) {
            nativeEditor.putString(key, value)
        }

        override fun set(key: String, value: Boolean) {
            nativeEditor.putBoolean(key, value)
        }

        override fun set(key: String, value: Float) {
            nativeEditor.putFloat(key, value)
        }

        override fun set(key: String, value: Long) {
            nativeEditor.putLong(key, value)
        }

        override fun set(key: String, value: Int) {
            nativeEditor.putInt(key, value)
        }

        override fun save() {
            nativeEditor.commit()
        }

        override fun saveAsync() {
            nativeEditor.apply()
        }
    }
}
