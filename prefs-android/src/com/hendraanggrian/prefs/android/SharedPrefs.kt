@file:JvmMultifileClass
@file:JvmName("PrefsAndroidKt")
@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.prefs.android

import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.hendraanggrian.prefs.Prefs
import com.hendraanggrian.prefs.PrefsEditor
import com.hendraanggrian.prefs.PrefsSaver
import com.hendraanggrian.prefs.WritablePrefs

/**
 * Create a [SharedPrefs] from shared preferences.
 *
 * @param preferences source of this preferences.
 */
fun Prefs.Companion.of(preferences: SharedPreferences): SharedPrefs =
    SharedPrefs(preferences)

/**
 * Create a [SharedPrefs] from context.
 *
 * @param context source of this preferences.
 */
fun Prefs.Companion.of(context: Context): SharedPrefs =
    of(PreferenceManager.getDefaultSharedPreferences(context))

/**
 * Create a [SharedPrefs] from fragment.
 *
 * @param fragment source of this preferences.
 */
fun Prefs.Companion.of(fragment: Fragment): SharedPrefs =
    of(fragment.activity)

/**
 * Create a [SharedPrefs] from fragment.
 *
 * @param fragment source of this preferences.
 */
fun Prefs.Companion.of(fragment: androidx.fragment.app.Fragment): SharedPrefs =
    of(checkNotNull(fragment.context) { "Context is not yet attached to this fragment" })

/**
 * Create a [SharedPrefs] from shared preferences.
 * Preferences created from this function will have an automatic value conversion.
 *
 * @param preferences source of this preferences.
 */
fun Prefs.Companion.safeOf(preferences: SharedPreferences): SharedPrefs =
    SafeSharedPrefs(preferences)

/**
 * Create a [SharedPrefs] from context.
 * Preferences created from this function will have an automatic value conversion.
 *
 * @param context source of this preferences.
 */
fun Prefs.Companion.safeOf(context: Context): SharedPrefs =
    safeOf(PreferenceManager.getDefaultSharedPreferences(context))

/**
 * Create a [SharedPrefs] from fragment.
 * Preferences created from this function will have an automatic value conversion.
 *
 * @param fragment source of this preferences.
 */
fun Prefs.Companion.safeOf(fragment: Fragment): SharedPrefs =
    safeOf(fragment.activity)

/**
 * Create a [SharedPrefs] from fragment.
 * Preferences created from this function will have an automatic value conversion.
 *
 * @param fragment source of this preferences.
 */
fun Prefs.Companion.safeOf(fragment: androidx.fragment.app.Fragment): SharedPrefs =
    safeOf(checkNotNull(fragment.context) { "Context is not yet attached to this fragment" })

/**
 * Convenient method to bind [SharedPrefs] to target.
 *
 * @param preferences source of this preferences.
 * @param target parent of fields that will be binded to.
 */
fun Prefs.Companion.bind(preferences: SharedPreferences, target: Any): PrefsSaver =
    of(preferences).bind(target)

/**
 * Convenient method to bind [SharedPrefs] to target.
 *
 * @param context source of this preferences.
 * @param target parent of fields that will be binded to.
 */
fun Prefs.Companion.bind(context: Context, target: Any = context): PrefsSaver =
    of(context).bind(target)

/**
 * Convenient method to bind [SharedPrefs] to target.
 *
 * @param fragment source of this preferences.
 * @param target parent of fields that will be binded to.
 */
fun Prefs.Companion.bind(fragment: Fragment, target: Any = fragment): PrefsSaver =
    of(fragment).bind(target)

/**
 * Convenient method to bind [SharedPrefs] to target.
 *
 * @param fragment source of this preferences.
 * @param target parent of fields that will be binded to.
 */
fun Prefs.Companion.bind(fragment: androidx.fragment.app.Fragment, target: Any = fragment): PrefsSaver =
    of(fragment).bind(target)

/**
 * Convenient method to bind [SharedPrefs] to target.
 * Preferences created from this function will have an automatic value conversion.
 *
 * @param preferences source of this preferences.
 * @param target parent of fields that will be binded to.
 */
fun Prefs.Companion.safeBind(preferences: SharedPreferences, target: Any): PrefsSaver =
    safeOf(preferences).bind(target)

/**
 * Convenient method to bind [SharedPrefs] to target.
 * Preferences created from this function will have an automatic value conversion.
 *
 * @param context source of this preferences.
 * @param target parent of fields that will be binded to.
 */
fun Prefs.Companion.safeBind(context: Context, target: Any = context): PrefsSaver =
    safeOf(context).bind(target)

/**
 * Convenient method to bind [SharedPrefs] to target.
 * Preferences created from this function will have an automatic value conversion.
 *
 * @param fragment source of this preferences.
 * @param target parent of fields that will be binded to.
 */
fun Prefs.Companion.safeBind(fragment: Fragment, target: Any = fragment): PrefsSaver =
    safeOf(fragment).bind(target)

/**
 * Convenient method to bind [SharedPrefs] to target.
 * Preferences created from this function will have an automatic value conversion.
 *
 * @param fragment source of this preferences.
 * @param target parent of fields that will be binded to.
 */
fun Prefs.Companion.safeBind(fragment: androidx.fragment.app.Fragment, target: Any = fragment): PrefsSaver =
    safeOf(fragment).bind(target)

open class SharedPrefs internal constructor(protected val nativePreferences: SharedPreferences) : WritablePrefs {

    fun setOnChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        nativePreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    fun removeOnChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        nativePreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

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

    override val editor: PrefsEditor
        get() = Editor(this, nativePreferences.edit())

    open class Editor(source: Prefs, private val nativeEditor: SharedPreferences.Editor) : Prefs by source,
        PrefsEditor {

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

internal class SafeSharedPrefs(nativePreferences: SharedPreferences) : SharedPrefs(nativePreferences) {
    override fun getDouble(key: String): Double? = get(key)?.toDouble()
    override fun getShort(key: String): Short? = get(key)?.toShort()
    override fun getByte(key: String): Byte? = get(key)?.toByte()
    override val editor: PrefsEditor get() = Editor(this, nativePreferences.edit())

    class Editor(source: Prefs, nativeEditor: SharedPreferences.Editor) : SharedPrefs.Editor(source, nativeEditor) {
        override fun set(key: String, value: Double) = set(key, value.toString())
        override fun set(key: String, value: Short) = set(key, value.toString())
        override fun set(key: String, value: Byte) = set(key, value.toString())
    }
}
