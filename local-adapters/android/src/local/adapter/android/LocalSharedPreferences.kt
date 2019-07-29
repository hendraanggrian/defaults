package local.adapter.android

import android.content.SharedPreferences
import local.LocalWriter
import local.ReadableLocal
import local.EditableLocal

open class LocalSharedPreferences internal constructor(internal val nativePreferences: SharedPreferences) :
    EditableLocal {

    override fun contains(key: String): Boolean = key in nativePreferences

    override fun get(key: String): String? =
        nativePreferences.getString(key, null)

    override fun getOrDefault(key: String, defaultValue: String): String =
        nativePreferences.getString(key, defaultValue)!!

    override fun getBoolean(key: String): Boolean? =
        nativePreferences.getBoolean(key, false)

    override fun getBooleanOrDefault(key: String, defaultValue: Boolean): Boolean =
        nativePreferences.getBoolean(key, defaultValue)

    override fun getDouble(key: String): Double? = throw UnsupportedOperationException()

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

    override fun getShort(key: String): Short? = throw UnsupportedOperationException()

    override fun getByte(key: String): Byte? = throw UnsupportedOperationException()

    override val writer: LocalWriter
        get() = Editor(this, nativePreferences.edit())

    open class Editor(source: ReadableLocal, private val nativeEditor: SharedPreferences.Editor) :
        ReadableLocal by source, LocalWriter {

        override fun remove(key: String) {
            nativeEditor.remove(key)
        }

        override fun clear() {
            nativeEditor.clear()
        }

        override fun set(key: String, value: String) {
            nativeEditor.putString(key, value)
        }

        override fun set(key: String, value: Boolean) {
            nativeEditor.putBoolean(key, value)
        }

        override fun set(key: String, value: Double): Unit = throw UnsupportedOperationException()

        override fun set(key: String, value: Float) {
            nativeEditor.putFloat(key, value)
        }

        override fun set(key: String, value: Long) {
            nativeEditor.putLong(key, value)
        }

        override fun set(key: String, value: Int) {
            nativeEditor.putInt(key, value)
        }

        override fun set(key: String, value: Short): Unit = throw UnsupportedOperationException()

        override fun set(key: String, value: Byte): Unit = throw UnsupportedOperationException()

        override fun save() {
            nativeEditor.commit()
        }

        override fun saveAsync() {
            nativeEditor.apply()
        }
    }
}
