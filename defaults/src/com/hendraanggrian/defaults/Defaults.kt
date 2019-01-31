@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.defaults

import androidx.annotation.AnyThread
import androidx.annotation.WorkerThread
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.util.WeakHashMap

/** Bind fields in target (this) annotated with [BindDefault] from [source]. */
fun Any.bindDefaults(source: ReadableDefaults): Defaults.Saver {
    val targetClass = javaClass
    Defaults.DEBUGGER?.invoke("Looking up binding for ${targetClass.name}")
    val constructor =
        Defaults.findBindingConstructor(targetClass)
    if (constructor == null) {
        Defaults.DEBUGGER?.invoke("${targetClass.name} binding not found, returning empty Committer.")
        return Defaults.Saver.EMPTY
    }
    try {
        return constructor.newInstance(this, source)
    } catch (e: IllegalAccessException) {
        throw RuntimeException("Unable to invoke \$constructor", e)
    } catch (e: InstantiationException) {
        throw RuntimeException("Unable to invoke \$constructor", e)
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

/**
 * @param E local settings editor
 */
interface Defaults<E : Defaults.Editor> : ReadableDefaults {

    /** Often used and extended to create [Defaults] instance from ranges of input. */
    companion object {
        const val TAG = "com.hendraanggrian.defaults.Defaults"

        internal var DEBUGGER: DefaultsDebugger? = null
        private lateinit var BINDINGS: MutableMap<Class<*>, Constructor<Defaults.Saver>>

        /** Modify debugging behavior, default is none. */
        fun setDebug(debug: DefaultsDebugger?) {
            DEBUGGER = debug
        }

        @Suppress("UNCHECKED_CAST")
        internal fun findBindingConstructor(cls: Class<*>): Constructor<Defaults.Saver>? {
            if (!::BINDINGS.isInitialized) BINDINGS = WeakHashMap()
            var binding = BINDINGS[cls]
            if (binding != null) {
                DEBUGGER?.invoke("HIT: Cache found in binding weak map.")
                return binding
            }
            if (cls.name.startsWith("android.") || cls.name.startsWith("java.")) {
                DEBUGGER?.invoke("MISS: Reached framework class. Abandoning search.")
                return null
            }
            try {
                binding = cls.classLoader!!
                    .loadClass(cls.name + BindDefault.SUFFIX)
                    .getConstructor(
                        cls,
                        com.hendraanggrian.defaults.Defaults::class.java
                    ) as Constructor<Defaults.Saver>
                DEBUGGER?.invoke("HIT: Loaded binding class, caching in weak map.")
            } catch (e: ClassNotFoundException) {
                val superclass = cls.superclass
                DEBUGGER?.invoke("Not found. Trying superclass ${superclass!!.name}")
                binding =
                    findBindingConstructor(
                        superclass
                    )
            } catch (e: NoSuchMethodException) {
                throw RuntimeException("Unable to find binding constructor for \$name", e)
            }
            BINDINGS[cls] = binding!!
            return binding
        }
    }

    /**
     * When editor instance is created, resources must be available (e.g.: opening sql transaction).
     * Resources may be released upon `save` or `saveAsync`.
     */
    fun getEditor(): E

    /**
     * Convenient method to quickly open an editor and apply changes in dsl.
     *
     * @param edit receiver is [Defaults] for access to settings' contents, next param is [Writer]
     *        for custom editing.
     */
    infix operator fun invoke(edit: (Defaults<E>.(E) -> Unit)): Defaults<E> =
        apply { getEditor().also { edit(it) }.save() }

    /** Responsible of modifying settings. */
    interface Editor : Saver {

        /** Removes a setting. */
        operator fun minusAssign(key: String)

        /** Removes all settings. */
        fun reset()

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

    /** Base interface to save changes to local settings. */
    interface Saver {

        companion object {
            internal val EMPTY: Saver = object : Saver {
                override fun save() {}
                override fun saveAsync() {}
            }
        }

        /** Non-blocking save in the background. */
        @WorkerThread
        fun save()

        /** Blocking save. */
        @AnyThread
        fun saveAsync()
    }
}