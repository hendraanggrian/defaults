@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.defaults

import java.io.File
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.util.WeakHashMap
import java.util.prefs.Preferences

/** A set of readable local settings, modify value with editor created with [getEditor]. */
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

        /** Creates defaults instance from file. */
        inline operator fun get(file: File): PropertiesFileDefaults =
            PropertiesFileDefaults(file)

        /** Creates defaults instance from file. */
        inline fun bind(file: File, target: Any): DefaultsSaver =
            bind(get(file), target)

        /** Creates defaults instance from preferences. */
        inline operator fun get(preferences: Preferences): PreferencesDefaults =
            PreferencesDefaults(preferences)

        /** Creates defaults instance from file. */
        inline fun bind(preferences: Preferences, target: Any): DefaultsSaver =
            bind(get(preferences), target)

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
        private fun findBindingConstructor(cls: Class<*>): Constructor<DefaultsSaver>? {
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
     * Resources may be released upon [DefaultsEditor.save] or [DefaultsEditor.saveAsync].
     */
    fun getEditor(): E

    /** Convenient method to quickly open an editor and apply changes in dsl. */
    operator fun invoke(edit: (ReadableDefaults.(E) -> Unit)): Defaults<E> =
        apply { getEditor().also { edit(it) }.save() }
}