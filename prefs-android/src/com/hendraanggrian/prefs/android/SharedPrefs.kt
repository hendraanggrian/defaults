@file:JvmMultifileClass
@file:JvmName("PrefsAndroidKt")
@file:Suppress("NOTHING_TO_INLINE", "DEPRECATION", "DeprecatedCallableAddReplaceWith")

package com.hendraanggrian.prefs.android

import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.AnyThread
import androidx.annotation.WorkerThread
import androidx.preference.PreferenceManager
import com.hendraanggrian.prefs.BindPref
import com.hendraanggrian.prefs.EditablePrefs
import com.hendraanggrian.prefs.Prefs

/**
 * Create a [SharedPrefs] from source [SharedPreferences].
 * @param source native Android preferences.
 * @return preferences that reads/writes to [SharedPreferences].
 */
fun Prefs.Companion.of(source: SharedPreferences): SharedPrefs = SharedPrefs(source)

/**
 * Create a [SharedPrefs] from source [Context].
 * @param source application context.
 * @return preferences that reads/writes to [SharedPreferences].
 */
fun Prefs.Companion.of(source: Context): SharedPrefs =
    of(PreferenceManager.getDefaultSharedPreferences(source))

/**
 * Create a [SharedPrefs] from source [Fragment].
 * @param source deprecated fragment.
 * @return preferences that reads/writes to [SharedPreferences].
 */
@Deprecated("Use support androidx.fragment.app.Fragment.")
fun Prefs.Companion.of(source: Fragment): SharedPrefs = of(source.activity)

/**
 * Create a [SharedPrefs] from source [androidx.fragment.app.Fragment].
 * @param source support fragment.
 * @return preferences that reads/writes to [SharedPreferences].
 */
fun Prefs.Companion.of(source: androidx.fragment.app.Fragment): SharedPrefs =
    of(checkNotNull(source.context) { "Context is not yet attached to this fragment" })

/**
 * Bind fields annotated with [BindPref] from source [SharedPrefs].
 * @param source native Android preferences.
 * @param target fields' owner.
 * @return saver instance to apply changes made to the fields.
 * @throws RuntimeException when constructor of binding class cannot be found.
 */
inline fun Prefs.Companion.bind(source: SharedPreferences, target: Any): Prefs.Saver =
    bind(of(source), target)

/**
 * Bind fields annotated with [BindPref] from source [Context].
 * @param source application context.
 * @param target fields' owner.
 * @return saver instance to apply changes made to the fields.
 * @throws RuntimeException when constructor of binding class cannot be found.
 */
inline fun Prefs.Companion.bind(source: Context, target: Any = source): Prefs.Saver =
    bind(of(source), target)

/**
 * Bind fields annotated with [BindPref] from source [Fragment].
 * @param source deprecated fragment.
 * @param target fields' owner.
 * @return saver instance to apply changes made to the fields.
 * @throws RuntimeException when constructor of binding class cannot be found.
 */
@Deprecated("Use support androidx.fragment.app.Fragment.")
inline fun Prefs.Companion.bind(source: Fragment, target: Any = source): Prefs.Saver =
    bind(of(source), target)

/**
 * Bind fields annotated with [BindPref] from source [androidx.fragment.app.Fragment].
 * @param source a support fragment.
 * @param target fields' owner.
 * @return saver instance to apply changes made to the fields.
 * @throws RuntimeException when constructor of binding class cannot be found.
 */
inline fun Prefs.Companion.bind(source: androidx.fragment.app.Fragment, target: Any = source): Prefs.Saver =
    bind(of(source), target)

class SharedPrefs internal constructor(private val nativePreferences: SharedPreferences) : EditablePrefs {

    fun setOnChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        nativePreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    fun removeOnChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        nativePreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    override fun contains(key: String): Boolean = key in nativePreferences

    override fun get(key: String): String? = nativePreferences.getString(key, null)
    override fun getOrDefault(key: String, defaultValue: String): String =
        nativePreferences.getString(key, defaultValue)!!

    override fun getBoolean(key: String): Boolean? = nativePreferences.getBoolean(key, false)
    override fun getBooleanOrDefault(key: String, defaultValue: Boolean): Boolean =
        nativePreferences.getBoolean(key, defaultValue)

    override fun getDouble(key: String): Double? = throw UnsupportedOperationException()

    override fun getFloat(key: String): Float? = nativePreferences.getFloat(key, 0f)
    override fun getFloatOrDefault(key: String, defaultValue: Float): Float =
        nativePreferences.getFloat(key, defaultValue)

    override fun getLong(key: String): Long? = nativePreferences.getLong(key, 0L)
    override fun getLongOrDefault(key: String, defaultValue: Long): Long = nativePreferences.getLong(key, defaultValue)

    override fun getInt(key: String): Int? = nativePreferences.getInt(key, 0)
    override fun getIntOrDefault(key: String, defaultValue: Int): Int =
        nativePreferences.getInt(key, defaultValue)

    override fun getShort(key: String): Short? = throw UnsupportedOperationException()
    override fun getByte(key: String): Byte? = throw UnsupportedOperationException()

    override val editor: Prefs.Editor get() = Editor(nativePreferences.edit())

    class Editor(private val nativeEditor: SharedPreferences.Editor) : Prefs.Editor {
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

        /**
         * @see SharedPreferences.Editor.commit
         */
        @WorkerThread override fun save() {
            nativeEditor.commit()
        }

        /**
         * @see SharedPreferences.Editor.apply
         */
        @AnyThread fun saveAsync() = nativeEditor.apply()
    }
}
