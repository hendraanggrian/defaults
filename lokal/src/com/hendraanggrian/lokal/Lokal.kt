@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.lokal

import androidx.annotation.AnyThread
import androidx.annotation.WorkerThread
import java.io.File
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.util.WeakHashMap
import java.util.prefs.Preferences

/** A set of readable local settings, modify value with editor created with [getEditor]. */
interface Lokal<E : Lokal.Editor> : ReadableLokal {

    companion object {
        private var debugger: Debugger? = null

        /** Modify debugging behavior, default is null. */
        fun setDebugger(debug: Debugger?) {
            debugger = debug
        }

        internal fun debug(message: String) {
            debugger?.invoke(message)
        }
    }

    /**
     * When editor instance is created, resources must be available (e.g.: opening sql transaction).
     * Resources may be released upon [LokalEditor.save] or [LokalEditor.saveAsync].
     */
    fun getEditor(): E

    /** Convenient method to quickly open an editor and apply changes in dsl. */
    operator fun invoke(edit: (ReadableLokal.(E) -> Unit)): Lokal<E> =
        apply { getEditor().also { edit(it) }.save() }

    /** Base interface to save changes to local settings. */
    interface Saver {

        companion object {
            internal val EMPTY: Saver = object : Saver {
                override fun save() = saveAsync()
                override fun saveAsync() = debug("WARNING: Saving an empty instance.")
            }
        }

        /** Non-blocking save in the background. */
        @WorkerThread
        fun save()

        /** Blocking save. */
        @AnyThread
        fun saveAsync()
    }

    /** Settings value modifier with Kotlin operators. */
    interface Editor : Saver {

        /** Removes a setting. */
        fun remove(key: String)

        /** Convenient operator to remove a setting. */
        operator fun minusAssign(key: String) = remove(key)

        /** Removes all settings. */
        fun clear()

        /** Add/change string value. */
        operator fun set(key: String, value: String?)

        /** Add/change boolean value. */
        operator fun set(key: String, value: Boolean)

        /** Add/change double value. */
        operator fun set(key: String, value: Double)

        /** Add/change float value. */
        operator fun set(key: String, value: Float)

        /** Add/change long value. */
        operator fun set(key: String, value: Long)

        /** Add/change int value. */
        operator fun set(key: String, value: Int)

        /** Add/change short value. */
        operator fun set(key: String, value: Short)

        /** Add/change byte value. */
        operator fun set(key: String, value: Byte)
    }

    /** Represents a runnable with string param. */
    open class Debugger(debug: (String) -> Unit) : ((String) -> Unit) by debug {

        /** Features often use this companion as receiver. */
        companion object {

            /** Default debugger, which just prints in system. */
            val System: Debugger get() = Debugger { println(it) }
        }
    }
}

/** Creates lokal instance from file. */
inline fun File.toLokal(): LokalProperties =
    LokalProperties(this)

/** Creates lokal instance from file. */
inline fun File.bindLokal(target: Any): Lokal.Saver =
    toLokal().bindLokal(target)

/** Creates lokal instance from preferences. */
inline fun Preferences.toLokal(): LokalPreferences =
    LokalPreferences(this)

/** Creates lokal instance from file. */
inline fun Preferences.bindLokal(target: Any): Lokal.Saver =
    toLokal().bindLokal(target)

/** Bind fields in target (this) annotated with [BindLokal] from this lokal. */
fun ReadableLokal.bindLokal(target: Any): Lokal.Saver {
    val targetClass = target.javaClass
    Lokal.debug("Looking up binding for ${targetClass.name}")
    val constructor = findBindingConstructor(targetClass)
    if (constructor == null) {
        Lokal.debug("${targetClass.name} binding not found, returning empty saver.")
        return Lokal.Saver.EMPTY
    }
    try {
        return constructor.newInstance(target, this)
    } catch (e: IllegalAccessException) {
        throw RuntimeException("Unable to invoke $constructor", e)
    } catch (e: InstantiationException) {
        throw RuntimeException("Unable to invoke $constructor", e)
    } catch (e: InvocationTargetException) {
        val cause = e.cause
        if (cause is RuntimeException) {
            throw cause
        }
        if (cause is Error) {
            throw cause
        }
        throw RuntimeException("Unable to create binding instance.", cause)
    }
}

private lateinit var bindings: MutableMap<Class<*>, Constructor<Lokal.Saver>>

@Suppress("UNCHECKED_CAST")
private fun findBindingConstructor(cls: Class<*>): Constructor<Lokal.Saver>? {
    if (!::bindings.isInitialized) {
        bindings = WeakHashMap()
    }
    var binding = bindings[cls]
    if (binding != null) {
        Lokal.debug("HIT: Cache found in binding weak map.")
        return binding
    }
    if (cls.name.startsWith("android.") || cls.name.startsWith("java.")) {
        Lokal.debug("MISS: Reached framework class. Abandoning search.")
        return null
    }
    try {
        binding = cls.classLoader!!
            .loadClass(cls.name + BindLokal.SUFFIX)
            .getConstructor(cls, ReadableLokal::class.java) as Constructor<Lokal.Saver>
        Lokal.debug("HIT: Loaded binding class, caching in weak map.")
    } catch (e: ClassNotFoundException) {
        val superclass = cls.superclass
        Lokal.debug("Not found. Trying superclass ${superclass!!.name}")
        binding = findBindingConstructor(superclass)
    } catch (e: NoSuchMethodException) {
        throw RuntimeException("Unable to find binding constructor for ${cls.name}", e)
    }
    bindings[cls] = binding!!
    return binding
}