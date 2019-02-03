@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.defaults

import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.util.WeakHashMap

/**
 * @param E local settings editor
 */
interface Defaults<E : DefaultsEditor> : ReadableDefaults {

    /** Often used and extended to create [Defaults] instance from ranges of input. */
    companion object {
        const val TAG = "com.hendraanggrian.defaults.Defaults"

        private var debugger: DefaultsDebugger? = null
        private lateinit var bindings: MutableMap<Class<*>, Constructor<DefaultsSaver>>

        /** Modify debugging behavior, default is none. */
        fun setDebugger(debug: DefaultsDebugger?) {
            debugger = debug
        }

        internal fun debug(message: String) {
            debugger?.invoke(message)
        }

        /** Bind fields in target (this) annotated with [BindDefault] from this defaults. */
        fun bind(source: ReadableDefaults, target: Any): DefaultsSaver {
            val targetClass = target.javaClass
            debug("Looking up binding for ${targetClass.name}")
            val constructor = findBindingConstructor(targetClass)
            if (constructor == null) {
                debug("${targetClass.name} binding not found, returning empty saver.")
                return DefaultsSaver.EMPTY
            }
            try {
                return constructor.newInstance(target, source)
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

        @Suppress("UNCHECKED_CAST")
        internal fun findBindingConstructor(cls: Class<*>): Constructor<DefaultsSaver>? {
            if (!::bindings.isInitialized) bindings = WeakHashMap()
            var binding = bindings[cls]
            if (binding != null) {
                debug("HIT: Cache found in binding weak map.")
                return binding
            }
            if (cls.name.startsWith("android.") || cls.name.startsWith("java.")) {
                debug("MISS: Reached framework class. Abandoning search.")
                return null
            }
            try {
                binding = cls.classLoader!!
                    .loadClass(cls.name + BindDefault.SUFFIX)
                    .getConstructor(
                        cls,
                        com.hendraanggrian.defaults.Defaults::class.java
                    ) as Constructor<DefaultsSaver>
                debug("HIT: Loaded binding class, caching in weak map.")
            } catch (e: ClassNotFoundException) {
                val superclass = cls.superclass
                debug("Not found. Trying superclass ${superclass!!.name}")
                binding =
                    findBindingConstructor(
                        superclass
                    )
            } catch (e: NoSuchMethodException) {
                throw RuntimeException("Unable to find binding constructor for \$name", e)
            }
            bindings[cls] = binding!!
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
     * @param edit receiver is [Defaults] for access to settings' contents, next param is editor
     *        for custom editing.
     */
    operator fun invoke(edit: (ReadableDefaults.(E) -> Unit)): Defaults<E> =
        apply { getEditor().also { edit(it) }.save() }
}