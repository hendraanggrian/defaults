@file:JvmMultifileClass
@file:JvmName("PrefsJvmKt")
@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.prefs.jvm

import com.hendraanggrian.prefs.Prefs
import com.hendraanggrian.prefs.SimplePrefs
import java.io.OutputStream
import java.util.prefs.NodeChangeListener
import java.util.prefs.PreferenceChangeListener
import java.util.prefs.Preferences
import kotlin.reflect.KClass

/**
 * Create a [JvmPrefs] from source [Preferences].
 * @param source native JVM preferences.
 * @return preferences that reads/writes to [Preferences].
 */
fun Prefs.Companion.of(source: Preferences): JvmPrefs = JvmPrefs(source)

/**
 * Create a user root [JvmPrefs] from Kotlin class.
 * @param type the class for whose package a user preference node is desired.
 * @param paths path to specific children node, or empty for root.
 * @return preferences that reads/writes to [Preferences].
 */
fun Prefs.Companion.userNode(type: KClass<*>, vararg paths: String): JvmPrefs =
    JvmPrefs(Preferences.userNodeForPackage(type.java).nodes(*paths))

/**
 * Create a user root [JvmPrefs] from Kotlin reified type.
 * @param T the class for whose package a user preference node is desired.
 * @param paths path to specific children node, or empty for root.
 * @return preferences that reads/writes to [Preferences].
 */
inline fun <reified T> Prefs.Companion.userNode(vararg paths: String): JvmPrefs =
    userNode(T::class, *paths)

/**
 * Create a system root [JvmPrefs] from Kotlin class.
 * @param type the class for whose package a user preference node is desired.
 * @param paths path to specific children node, or empty for root.
 * @return preferences that reads/writes to [Preferences].
 */
fun Prefs.Companion.systemNode(type: KClass<*>, vararg paths: String): JvmPrefs =
    JvmPrefs(Preferences.systemNodeForPackage(type.java).nodes(*paths))

/**
 * Create a system root [JvmPrefs] from Kotlin reified type.
 * @param T the class for whose package a user preference node is desired.
 * @param paths path to specific children node, or empty for root.
 * @return preferences that reads/writes to [Preferences].
 */
inline fun <reified T> Prefs.Companion.systemNode(vararg paths: String): JvmPrefs =
    systemNode(T::class, *paths)

/**
 * Create a user root [JvmPrefs].
 * @param paths path to specific children node, or empty for root.
 * @return preferences that reads/writes to [Preferences].
 */
fun Prefs.Companion.userRoot(vararg paths: String): JvmPrefs =
    JvmPrefs(Preferences.userRoot().nodes(*paths))

/**
 * Create a system root [JvmPrefs].
 * @param paths path to specific children node, or empty for root.
 * @return preferences that reads/writes to [Preferences].
 */
fun Prefs.Companion.systemRoot(vararg paths: String): JvmPrefs =
    JvmPrefs(Preferences.systemRoot().nodes(*paths))

private fun Preferences.nodes(vararg paths: String): Preferences {
    var root = this
    paths.forEach { root = root.node(it) }
    return root
}

class JvmPrefs internal constructor(private val nativePreferences: Preferences) : SimplePrefs {

    val keys: Array<String> get() = nativePreferences.keys()

    val childrenNames: Array<String> get() = nativePreferences.childrenNames()

    val parent: JvmPrefs get() = JvmPrefs(nativePreferences.parent())

    fun node(pathName: String): JvmPrefs = JvmPrefs(nativePreferences.node(pathName))

    fun nodeExists(pathName: String): Boolean = nativePreferences.nodeExists(pathName)

    fun removeNode(): Unit = nativePreferences.removeNode()

    val name: String get() = nativePreferences.name()

    val absolutePath: String get() = nativePreferences.absolutePath()

    fun isUserNode(): Boolean = nativePreferences.isUserNode

    fun addPreferenceChangeListener(listener: PreferenceChangeListener): PreferenceChangeListener =
        listener.also { nativePreferences.addPreferenceChangeListener(it) }

    fun removePreferenceChangeListener(listener: PreferenceChangeListener) =
        nativePreferences.removePreferenceChangeListener(listener)

    fun addNodeChangeListener(listener: NodeChangeListener): NodeChangeListener =
        listener.also { nativePreferences.addNodeChangeListener(it) }

    fun removeNodeChangeListener(listener: NodeChangeListener) =
        nativePreferences.removeNodeChangeListener(listener)

    fun exportNode(stream: OutputStream) = nativePreferences.exportNode(stream)

    fun exportSubtree(stream: OutputStream) = nativePreferences.exportSubtree(stream)

    override fun contains(key: String): Boolean = nativePreferences.nodeExists(key)

    override fun get(key: String): String? = nativePreferences.get(key, null)
    override fun getOrDefault(key: String, defaultValue: String): String = nativePreferences.get(key, defaultValue)

    override fun getBoolean(key: String): Boolean? = nativePreferences.getBoolean(key, false)
    override fun getBooleanOrDefault(key: String, defaultValue: Boolean): Boolean =
        nativePreferences.getBoolean(key, defaultValue)

    override fun getDouble(key: String): Double? = nativePreferences.getDouble(key, 0.0)
    override fun getDoubleOrDefault(key: String, defaultValue: Double): Double =
        nativePreferences.getDouble(key, defaultValue)

    override fun getFloat(key: String): Float? = nativePreferences.getFloat(key, 0f)
    override fun getFloatOrDefault(key: String, defaultValue: Float): Float =
        nativePreferences.getFloat(key, defaultValue)

    override fun getLong(key: String): Long? = nativePreferences.getLong(key, 0L)
    override fun getLongOrDefault(key: String, defaultValue: Long): Long = nativePreferences.getLong(key, defaultValue)

    override fun getInt(key: String): Int? = nativePreferences.getInt(key, 0)
    override fun getIntOrDefault(key: String, defaultValue: Int): Int = nativePreferences.getInt(key, defaultValue)

    override fun getShort(key: String): Short? = throw UnsupportedOperationException()
    override fun getByte(key: String): Byte? = throw UnsupportedOperationException()

    override fun remove(key: String) = nativePreferences.remove(key)

    override fun clear() = nativePreferences.clear()

    override fun set(key: String, value: String?) = nativePreferences.put(key, value)
    override fun set(key: String, value: Boolean) = nativePreferences.putBoolean(key, value)
    override fun set(key: String, value: Double) = nativePreferences.putDouble(key, value)
    override fun set(key: String, value: Float) = nativePreferences.putFloat(key, value)
    override fun set(key: String, value: Long) = nativePreferences.putLong(key, value)
    override fun set(key: String, value: Int) = nativePreferences.putInt(key, value)
    override fun set(key: String, value: Short): Unit = throw UnsupportedOperationException()
    override fun set(key: String, value: Byte): Unit = throw UnsupportedOperationException()

    override fun save() {
        nativePreferences.sync()
        nativePreferences.flush()
    }
}
