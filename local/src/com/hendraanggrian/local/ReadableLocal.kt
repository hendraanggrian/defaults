@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.local

import com.hendraanggrian.local.jvm.LocalPreferences
import com.hendraanggrian.local.jvm.LocalProperties
import java.io.File
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.util.WeakHashMap
import java.util.prefs.Preferences

/** A set of readable local settings, modify value with editor created with [getEditor]. */
interface ReadableLocal {

    companion object {
        private var debugger: LocalDebugger? = null

        /** Modify debugging behavior, default is null. */
        fun setDebugger(debug: LocalDebugger?) {
            debugger = debug
        }

        internal fun debug(message: String) {
            debugger?.invoke(message)
        }
    }

    /** Checks if a setting exists. */
    operator fun contains(key: String): Boolean

    /** Returns string value. */
    operator fun get(key: String): String?

    /** Returns string value, or [defaultValue] if no such value exists. */
    fun getOrDefault(key: String, defaultValue: String): String =
        findValue(key, defaultValue) { get(it) }

    /** Returns string value, or [defaultValue] if no such value exists. */
    fun getOrElse(key: String, defaultValue: () -> String): String =
        getOrDefault(key, defaultValue())

    /** Returns boolean value. */
    fun getBoolean(key: String): Boolean?

    /** Returns boolean value, or [defaultValue] if no such value exists. */
    fun getBooleanOrDefault(key: String, defaultValue: Boolean): Boolean =
        findValue(key, defaultValue) { getBoolean(it) }

    /** Returns boolean value, or [defaultValue] if no such value exists. */
    fun getBooleanOrElse(key: String, defaultValue: () -> Boolean): Boolean =
        getBooleanOrDefault(key, defaultValue())

    /** Returns double value. */
    fun getDouble(key: String): Double?

    /** Returns double value, or [defaultValue] if no such value exists. */
    fun getDoubleOrDefault(key: String, defaultValue: Double): Double =
        findValue(key, defaultValue) { getDouble(it) }

    /** Returns double value, or [defaultValue] if no such value exists. */
    fun getDoubleOrElse(key: String, defaultValue: () -> Double): Double =
        getDoubleOrDefault(key, defaultValue())

    /** Returns float value. */
    fun getFloat(key: String): Float?

    /** Returns float value, or [defaultValue] if no such value exists. */
    fun getFloatOrDefault(key: String, defaultValue: Float): Float =
        findValue(key, defaultValue) { getFloat(it) }

    /** Returns float value, or [defaultValue] if no such value exists. */
    fun getFloatOrElse(key: String, defaultValue: () -> Float): Float =
        getFloatOrDefault(key, defaultValue())

    /** Returns long value. */
    fun getLong(key: String): Long?

    /** Returns Long value, or [defaultValue] if no such value exists. */
    fun getLongOrDefault(key: String, defaultValue: Long): Long =
        findValue(key, defaultValue) { getLong(it) }

    /** Returns Long value, or [defaultValue] if no such value exists. */
    fun getLongOrElse(key: String, defaultValue: () -> Long): Long =
        getLongOrDefault(key, defaultValue())

    /** Returns int value. */
    fun getInt(key: String): Int?

    /** Returns int value, or [defaultValue] if no such value exists. */
    fun getIntOrDefault(key: String, defaultValue: Int): Int =
        findValue(key, defaultValue) { getInt(it) }

    /** Returns int value, or [defaultValue] if no such value exists. */
    fun getIntOrElse(key: String, defaultValue: () -> Int): Int =
        getIntOrDefault(key, defaultValue())

    /** Returns short value. */
    fun getShort(key: String): Short?

    /** Returns short value, or [defaultValue] if no such value exists. */
    fun getShortOrDefault(key: String, defaultValue: Short): Short =
        findValue(key, defaultValue) { getShort(it) }

    /** Returns short value, or [defaultValue] if no such value exists. */
    fun getShortOrElse(key: String, defaultValue: () -> Short): Short =
        getShortOrDefault(key, defaultValue())

    /** Returns byte value. */
    fun getByte(key: String): Byte?

    /** Returns byte value, or [defaultValue] if no such value exists. */
    fun getByteOrDefault(key: String, defaultValue: Byte): Byte =
        findValue(key, defaultValue) { getByte(it) }

    /** Returns byte value, or [defaultValue] if no such value exists. */
    fun getByteOrElse(key: String, defaultValue: () -> Byte): Byte =
        getByteOrDefault(key, defaultValue())

    private inline fun <T> findValue(
        key: String,
        defaultValue: T,
        getValue: (key: String) -> T?
    ): T {
        if (key !in this) {
            val value = getValue(key)
            if (value != null) {
                return value
            }
        }
        return defaultValue
    }
}

/** Creates local instance from file. */
inline fun File.toLocal(): LocalProperties =
    LocalProperties(this)

/** Creates local instance from file. */
inline fun File.bindLocal(target: Any): LocalSaver =
    toLocal().bindLocal(target)

/** Creates local instance from preferences. */
inline fun Preferences.toLocal(): LocalPreferences =
    LocalPreferences(this)

/** Creates local instance from file. */
inline fun Preferences.bindLocal(target: Any): LocalSaver =
    toLocal().bindLocal(target)

/** Bind fields in target (this) annotated with [Local] from this local. */
fun ReadableLocal.bindLocal(target: Any): LocalSaver {
    val targetClass = target.javaClass
    ReadableLocal.debug("Looking up binding for ${targetClass.name}")
    val constructor = findBindingConstructor(targetClass)
    if (constructor == null) {
        ReadableLocal.debug("${targetClass.name} binding not found, returning empty saver.")
        return LocalSaver.EMPTY
    }
    try {
        return constructor.newInstance(target, this)
    } catch (e: IllegalAccessException) {
        throw RuntimeException("Unable to invoke $constructor", e)
    } catch (e: InstantiationException) {
        throw RuntimeException("Unable to invoke $constructor", e)
    } catch (e: InvocationTargetException) {
        when (val cause = e.cause) {
            is RuntimeException -> throw cause
            is Error -> throw cause
            else -> throw RuntimeException("Unable to create binding instance.", cause)
        }
    }
}

private lateinit var bindings: MutableMap<Class<*>, Constructor<LocalSaver>>

@Suppress("UNCHECKED_CAST")
private fun findBindingConstructor(cls: Class<*>): Constructor<LocalSaver>? {
    if (!::bindings.isInitialized) {
        bindings = WeakHashMap()
    }
    var binding = bindings[cls]
    if (binding != null) {
        ReadableLocal.debug("HIT: Cache found in binding weak map.")
        return binding
    }
    if (cls.name.startsWith("android.") || cls.name.startsWith("java.")) {
        ReadableLocal.debug("MISS: Reached framework class. Abandoning search.")
        return null
    }
    try {
        binding = cls.classLoader!!
            .loadClass(cls.name + Local.SUFFIX)
            .getConstructor(cls, ReadableLocal::class.java) as Constructor<LocalSaver>
        ReadableLocal.debug("HIT: Loaded binding class, caching in weak map.")
    } catch (e: ClassNotFoundException) {
        val superclass = cls.superclass
        ReadableLocal.debug("Not found. Trying superclass ${superclass!!.name}")
        binding = findBindingConstructor(superclass)
    } catch (e: NoSuchMethodException) {
        throw RuntimeException("Unable to find binding constructor for ${cls.name}", e)
    }
    bindings[cls] = binding!!
    return binding
}
