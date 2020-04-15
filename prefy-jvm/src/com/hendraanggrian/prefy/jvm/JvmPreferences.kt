@file:JvmMultifileClass
@file:JvmName("PrefyJvmKt")
@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.prefy.jvm

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

/** A wrapper of [Preferences] with [WritablePreferences] implementation. */
class JvmPreferences internal constructor(private val nativePreferences: Preferences) : WritablePreferences {

    /**
     * Returns all of the keys that have an associated value in this preference node.
     * @see Preferences.keys
     */
    val keys: Array<String> get() = nativePreferences.keys()

    /**
     * Returns the names of the children of this preference node, relative to this node.
     * @see Preferences.childrenNames
     */
    val childrenNames: Array<String> get() = nativePreferences.childrenNames()

    /**
     * Returns the parent of this preference node, or `null` if this is the root.
     * @see Preferences.parent
     */
    val parent: JvmPreferences get() = JvmPreferences(nativePreferences.parent())

    /**
     * Returns the named preference node in the same tree as this node,
     * creating it and any of its ancestors if they do not already exist.
     * @see Preferences.node
     */
    fun node(pathName: String): JvmPreferences = JvmPreferences(nativePreferences.node(pathName))

    /**
     * Returns true if the named preference node exists in the same tree as this node.
     * @see Preferences.nodeExists
     */
    fun nodeExists(pathName: String): Boolean = nativePreferences.nodeExists(pathName)

    /**
     * Removes this preference node and all of its descendants,
     * invalidating any preferences contained in the removed nodes.
     * @see Preferences.removeNode
     */
    fun removeNode(): Unit = nativePreferences.removeNode()

    /**
     * Returns this preference node's name, relative to its parent.
     * @see Preferences.name
     */
    val name: String get() = nativePreferences.name()

    /**
     * Returns this preference node's absolute path name.
     * @see Preferences.absolutePath
     */
    val absolutePath: String get() = nativePreferences.absolutePath()

    /**
     * Returns `true` if this preference node is in the user
     * preference tree, `false` if it's in the system preference tree.
     * @see Preferences.isUserNode
     */
    fun isUserNode(): Boolean = nativePreferences.isUserNode

    /**
     * Registers the specified listener to receive `preference change events` for this preference node.
     * @see Preferences.addPreferenceChangeListener
     */
    fun addPreferenceChangeListener(listener: PreferenceChangeListener): Unit =
        nativePreferences.addPreferenceChangeListener(listener)

    /**
     * Convenient method to [addPreferenceChangeListener] with Kotlin function type.
     * @param action the callback that will run.
     * @return instance of Java listener, in case to [removePreferenceChangeListener] later.
     */
    inline fun onPreferenceChange(crossinline action: (key: String, value: String) -> Unit): PreferenceChangeListener =
        PreferenceChangeListener { evt -> action(evt.key, evt.newValue) }.also { addPreferenceChangeListener(it) }

    /**
     * Removes the specified preference change listener, so it no longer receives preference change events.
     * @see Preferences.removePreferenceChangeListener
     */
    fun removePreferenceChangeListener(listener: PreferenceChangeListener) =
        nativePreferences.removePreferenceChangeListener(listener)

    /**
     * Registers the specified listener to receive `node change events` for this node.
     * @see Preferences.addNodeChangeListener
     */
    fun addNodeChangeListener(listener: NodeChangeListener): Unit =
        nativePreferences.addNodeChangeListener(listener)

    /**
     * Removes the specified [NodeChangeListener], so it no longer receives change events.
     * @see Preferences.removeNodeChangeListener
     */
    fun removeNodeChangeListener(listener: NodeChangeListener) =
        nativePreferences.removeNodeChangeListener(listener)

    /**
     * Emits on the specified output stream an XML document representing all
     * of the preferences contained in this node (but not its descendants).
     * @see Preferences.exportNode
     */
    fun exportNode(stream: OutputStream) = nativePreferences.exportNode(stream)

    /**
     * Emits an XML document representing all of the preferences contained
     * in this node and all of its descendants.
     * @see Preferences.exportSubtree
     */
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
