@file:JvmMultifileClass
@file:JvmName("PrefyJvmKt")
@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.prefy.jvm

import com.hendraanggrian.prefy.BindPreference
import com.hendraanggrian.prefy.PreferencesSaver
import com.hendraanggrian.prefy.Prefy
import com.hendraanggrian.prefy.WritablePreferences
import java.io.OutputStream
import java.util.prefs.NodeChangeListener
import java.util.prefs.PreferenceChangeListener
import java.util.prefs.Preferences
import kotlin.reflect.KClass

/**
 * Create a [JvmPreferences] from source [Preferences].
 * @param source native JVM preferences.
 * @return preferences that reads/writes to [Preferences].
 */
operator fun Prefy.get(source: Preferences): JvmPreferences = JvmPreferences(source)

/**
 * Create a user root [JvmPreferences] from Kotlin class.
 * @param type the class for whose package a user preference node is desired.
 * @param paths path to specific children node, or empty for root.
 * @return preferences that reads/writes to [Preferences].
 */
fun Prefy.userNode(type: KClass<*>, vararg paths: String): JvmPreferences =
    JvmPreferences(Preferences.userNodeForPackage(type.java).nodes(*paths))

/**
 * Create a user root [JvmPreferences] from Kotlin reified type.
 * @param T the class for whose package a user preference node is desired.
 * @param paths path to specific children node, or empty for root.
 * @return preferences that reads/writes to [Preferences].
 */
inline fun <reified T> Prefy.userNode(vararg paths: String): JvmPreferences =
    userNode(T::class, *paths)

/**
 * Create a system root [JvmPreferences] from Kotlin class.
 * @param type the class for whose package a user preference node is desired.
 * @param paths path to specific children node, or empty for root.
 * @return preferences that reads/writes to [Preferences].
 */
fun Prefy.systemNode(type: KClass<*>, vararg paths: String): JvmPreferences =
    JvmPreferences(Preferences.systemNodeForPackage(type.java).nodes(*paths))

/**
 * Create a system root [JvmPreferences] from Kotlin reified type.
 * @param T the class for whose package a user preference node is desired.
 * @param paths path to specific children node, or empty for root.
 * @return preferences that reads/writes to [Preferences].
 */
inline fun <reified T> Prefy.systemNode(vararg paths: String): JvmPreferences =
    systemNode(T::class, *paths)

/**
 * Create a user root [JvmPreferences].
 * @param paths path to specific children node, or empty for root.
 * @return preferences that reads/writes to [Preferences].
 */
fun Prefy.userRoot(vararg paths: String): JvmPreferences =
    JvmPreferences(Preferences.userRoot().nodes(*paths))

/**
 * Create a system root [JvmPreferences].
 * @param paths path to specific children node, or empty for root.
 * @return preferences that reads/writes to [Preferences].
 */
fun Prefy.systemRoot(vararg paths: String): JvmPreferences =
    JvmPreferences(Preferences.systemRoot().nodes(*paths))

private inline fun Preferences.nodes(vararg paths: String): Preferences {
    var root = this
    paths.forEach { root = root.node(it) }
    return root
}

/**
 * Bind fields annotated with [BindPreference] from source [JvmPreferences].
 * @receiver target fields' owner.
 * @param source preferences to extract
 * @return saver instance to apply changes made to the fields.
 * @throws RuntimeException when constructor of binding class cannot be found.
 */
inline fun <T : Any> T.bindPreferences(source: JvmPreferences): PreferencesSaver =
    Prefy.bind(source, this)

/** A wrapper of [Preferences] with [WritablePreferences] implementation. */
class JvmPreferences internal constructor(private val nativePreferences: Preferences) :
    Preferences(), WritablePreferences {

    override fun put(key: String?, value: String?) = nativePreferences.put(key, value)
    override fun get(key: String?, def: String?): String = nativePreferences.get(key, def)
    override fun remove(key: String) = nativePreferences.remove(key)
    override fun clear() = nativePreferences.clear()
    override fun putInt(key: String?, value: Int) = nativePreferences.putInt(key, value)
    override fun getInt(key: String?, def: Int): Int = nativePreferences.getInt(key, def)
    override fun putLong(key: String?, value: Long) = nativePreferences.putLong(key, value)
    override fun getLong(key: String?, def: Long): Long = nativePreferences.getLong(key, def)
    override fun putBoolean(key: String?, value: Boolean) = nativePreferences.putBoolean(key, value)
    override fun getBoolean(key: String?, def: Boolean): Boolean = nativePreferences.getBoolean(key, def)
    override fun putFloat(key: String?, value: Float) = nativePreferences.putFloat(key, value)
    override fun getFloat(key: String?, def: Float): Float = nativePreferences.getFloat(key, def)
    override fun putDouble(key: String?, value: Double) = nativePreferences.putDouble(key, value)
    override fun getDouble(key: String?, def: Double): Double = nativePreferences.getDouble(key, def)
    override fun putByteArray(key: String?, value: ByteArray?) = nativePreferences.putByteArray(key, value)
    override fun getByteArray(key: String?, def: ByteArray?): ByteArray = nativePreferences.getByteArray(key, def)
    override fun keys(): Array<String> = nativePreferences.keys()
    override fun childrenNames(): Array<String> = nativePreferences.childrenNames()

    override fun parent(): JvmPreferences = JvmPreferences(nativePreferences.parent())
    override fun node(pathName: String?): JvmPreferences = JvmPreferences(nativePreferences.node(pathName))

    override fun nodeExists(pathName: String?): Boolean = nativePreferences.nodeExists(pathName)
    override fun removeNode() = nativePreferences.removeNode()
    override fun name(): String = nativePreferences.name()
    override fun absolutePath(): String = nativePreferences.absolutePath()
    override fun isUserNode(): Boolean = nativePreferences.isUserNode
    override fun toString(): String = nativePreferences.toString()
    override fun flush() = nativePreferences.flush()
    override fun sync() = nativePreferences.sync()

    override fun addPreferenceChangeListener(pcl: PreferenceChangeListener?) =
        nativePreferences.addPreferenceChangeListener(pcl)

    override fun removePreferenceChangeListener(pcl: PreferenceChangeListener?) =
        nativePreferences.removePreferenceChangeListener(pcl)

    override fun addNodeChangeListener(ncl: NodeChangeListener?) =
        nativePreferences.removeNodeChangeListener(ncl)

    override fun removeNodeChangeListener(ncl: NodeChangeListener?) =
        nativePreferences.removeNodeChangeListener(ncl)

    override fun exportNode(os: OutputStream?) = nativePreferences.exportNode(os)
    override fun exportSubtree(os: OutputStream?) = nativePreferences.exportSubtree(os)

    override fun contains(key: String): Boolean = nodeExists(key)

    override fun get(key: String): String? = get(key, null)
    override fun getOrDefault(key: String, defaultValue: String): String = get(key, defaultValue)

    override fun getBoolean(key: String): Boolean? = getBoolean(key, false)
    override fun getBooleanOrDefault(key: String, defaultValue: Boolean): Boolean = getBoolean(key, defaultValue)

    override fun getDouble(key: String): Double? = getDouble(key, 0.0)
    override fun getDoubleOrDefault(key: String, defaultValue: Double): Double = getDouble(key, defaultValue)

    override fun getFloat(key: String): Float? = getFloat(key, 0f)
    override fun getFloatOrDefault(key: String, defaultValue: Float): Float = getFloat(key, defaultValue)

    override fun getLong(key: String): Long? = getLong(key, 0L)
    override fun getLongOrDefault(key: String, defaultValue: Long): Long = getLong(key, defaultValue)

    override fun getInt(key: String): Int? = getInt(key, 0)
    override fun getIntOrDefault(key: String, defaultValue: Int): Int = getInt(key, defaultValue)

    override fun getShort(key: String): Short? = throw UnsupportedOperationException()
    override fun getByte(key: String): Byte? = throw UnsupportedOperationException()

    override fun set(key: String, value: String?) = put(key, value)
    override fun set(key: String, value: Boolean) = putBoolean(key, value)
    override fun set(key: String, value: Double) = putDouble(key, value)
    override fun set(key: String, value: Float) = putFloat(key, value)
    override fun set(key: String, value: Long) = putLong(key, value)
    override fun set(key: String, value: Int) = putInt(key, value)
    override fun set(key: String, value: Short): Unit = throw UnsupportedOperationException()
    override fun set(key: String, value: Byte): Unit = throw UnsupportedOperationException()

    override fun save() {
        sync()
        flush()
    }
}
