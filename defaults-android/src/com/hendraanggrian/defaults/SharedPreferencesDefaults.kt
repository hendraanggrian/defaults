package com.hendraanggrian.defaults

import android.content.SharedPreferences
import com.hendraanggrian.defaults.internal.ByteDefaultsEditorSupport
import com.hendraanggrian.defaults.internal.ByteReadableDefaultsSupport
import com.hendraanggrian.defaults.internal.DoubleDefaultsEditorSupport
import com.hendraanggrian.defaults.internal.DoubleReadableDefaultsSupport
import com.hendraanggrian.defaults.internal.ShortDefaultsEditorSupport
import com.hendraanggrian.defaults.internal.ShortReadableDefaultsSupport

class SharedPreferencesDefaults(private val nativePreferences: SharedPreferences) :
    Defaults<SharedPreferencesDefaults.Editor>,
    DoubleReadableDefaultsSupport,
    ShortReadableDefaultsSupport,
    ByteReadableDefaultsSupport {

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

    override fun getEditor(): Editor =
        Editor(nativePreferences.edit())

    fun toSharedPreferences(): SharedPreferences = nativePreferences

    class Editor(private val nativeEditor: SharedPreferences.Editor) : DefaultsEditor,
        DoubleDefaultsEditorSupport,
        ShortDefaultsEditorSupport,
        ByteDefaultsEditorSupport {

        override fun minusAssign(key: String) {
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