@file:JvmMultifileClass
@file:JvmName("PrefyAndroidKt")
@file:Suppress("NOTHING_TO_INLINE", "DEPRECATION", "DeprecatedCallableAddReplaceWith")

package com.hendraanggrian.prefy.android

import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.AnyThread
import androidx.annotation.WorkerThread
import androidx.preference.PreferenceManager
import com.hendraanggrian.prefy.BindPreference
import com.hendraanggrian.prefy.EditablePreferences
import com.hendraanggrian.prefy.PreferencesEditor
import com.hendraanggrian.prefy.PreferencesSaver
import com.hendraanggrian.prefy.Prefy
import com.hendraanggrian.prefy.bind

/**
 * Create a [AndroidPreferences] from source [SharedPreferences].
 * @param source native Android preferences.
 * @return preferences that reads/writes to [SharedPreferences].
 */
operator fun Prefy.get(source: SharedPreferences): AndroidPreferences = AndroidPreferences(source)

/**
 * Create a [AndroidPreferences] from source [Context].
 * @param source application context.
 * @return preferences that reads/writes to [SharedPreferences].
 */
operator fun Prefy.get(source: Context): AndroidPreferences =
    get(PreferenceManager.getDefaultSharedPreferences(source))

/**
 * Create a [AndroidPreferences] from source [Fragment].
 * @param source deprecated fragment.
 * @return preferences that reads/writes to [SharedPreferences].
 */
@Deprecated("Use support androidx.fragment.app.Fragment.")
operator fun Prefy.get(source: Fragment): AndroidPreferences =
    get(source.activity)

/**
 * Create a [AndroidPreferences] from source [androidx.fragment.app.Fragment].
 * @param source support fragment.
 * @return preferences that reads/writes to [SharedPreferences].
 */
operator fun Prefy.get(source: androidx.fragment.app.Fragment): AndroidPreferences =
    get(checkNotNull(source.context) { "Context is not yet attached to this fragment" })

/**
 * Bind fields annotated with [BindPreference] from source [Context].
 * @param source application context and also target fields' owner.
 * @return saver instance to apply changes made to the fields.
 * @throws RuntimeException when constructor of binding class cannot be found.
 */
inline fun Prefy.bind(source: Context): PreferencesSaver =
    get(source).bind(source)

/**
 * Bind fields annotated with [BindPreference] from source [Fragment].
 * @param source deprecated fragment and also target fields' owner.
 * @return saver instance to apply changes made to the fields.
 * @throws RuntimeException when constructor of binding class cannot be found.
 */
@Deprecated("Use support androidx.fragment.app.Fragment.")
inline fun Prefy.bind(source: Fragment): PreferencesSaver =
    get(source).bind(source)

/**
 * Bind fields annotated with [BindPreference] from source [androidx.fragment.app.Fragment].
 * @param source a support fragment and also target fields' owner.
 * @return saver instance to apply changes made to the fields.
 * @throws RuntimeException when constructor of binding class cannot be found.
 */
inline fun Prefy.bind(source: androidx.fragment.app.Fragment): PreferencesSaver =
    get(source).bind(source)

/** A wrapper of [SharedPreferences] with [EditablePreferences] implementation. */
class AndroidPreferences internal constructor(private val nativePreferences: SharedPreferences) :
    EditablePreferences<AndroidPreferences.Editor> {

    /**
     * Registers a callback to be invoked when a change happens to a preference.
     * @see SharedPreferences.registerOnSharedPreferenceChangeListener
     */
    fun setChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener): Unit =
        nativePreferences.registerOnSharedPreferenceChangeListener(listener)

    /**
     * Convenient method to [setChangeListener] with Kotlin function type.
     * @param action the callback that will run.
     * @return instance of Java listener, in case to [removeChangeListener] later.
     */
    inline fun onChange(crossinline action: (key: String) -> Unit): SharedPreferences.OnSharedPreferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key -> action(key) }.also { setChangeListener(it) }

    /**
     * Unregisters a previous callback.
     * @see SharedPreferences.unregisterOnSharedPreferenceChangeListener
     */
    fun removeChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener): Unit =
        nativePreferences.unregisterOnSharedPreferenceChangeListener(listener)

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

    override val editor: Editor get() = Editor(nativePreferences.edit())

    /** A wrapper of [SharedPreferences.Editor] with [PreferencesEditor] implementation. */
    class Editor internal constructor(private val nativeEditor: SharedPreferences.Editor) : PreferencesEditor {
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
         * Save changes blocking thread.
         * @see SharedPreferences.Editor.commit
         */
        @WorkerThread override fun save() {
            nativeEditor.commit()
        }

        /**
         * Save preferences changes in the background.
         * @see SharedPreferences.Editor.apply
         */
        @AnyThread fun saveAsync() = nativeEditor.apply()
    }
}
