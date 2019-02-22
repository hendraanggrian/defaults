@file:Suppress("NOTHING_TO_INLINE", "DEPRECATION")

package com.hendraanggrian.defaults

import java.io.File
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.util.WeakHashMap
import java.util.prefs.Preferences

/** A set of readable local settings, modify value with editor created with [getEditor]. */
interface Defaults<E : DefaultsEditor> : ReadableDefaults {

    companion object {
        private var debugger: DefaultsDebugger? = null

        /** Modify debugging behavior, default is null. */
        fun setDebugger(debug: DefaultsDebugger?) {
            debugger = debug
        }

        internal fun debug(message: String) {
            debugger?.invoke(message)
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

/** Creates defaults instance from file. */
inline fun File.toDefaults(): PropertiesDefaults = PropertiesDefaults(this)

/** Creates defaults instance from file. */
inline fun File.bindDefaults(target: Any): DefaultsSaver = toDefaults().bindDefaults(target)

/** Creates defaults instance from preferences. */
inline fun Preferences.toDefaults(): PreferencesDefaults = PreferencesDefaults(this)

/** Creates defaults instance from file. */
inline fun Preferences.bindDefaults(target: Any): DefaultsSaver =
    toDefaults().bindDefaults(target)

/** Bind fields in target (this) annotated with [BindDefault] from this defaults. */
fun ReadableDefaults.bindDefaults(target: Any): DefaultsSaver {
    val targetClass = target.javaClass
    Defaults.debug("Looking up binding for ${targetClass.name}")
    val constructor = findBindingConstructor(targetClass)
    if (constructor == null) {
        Defaults.debug("${targetClass.name} binding not found, returning empty saver.")
        return DefaultsSaver.EMPTY
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

private lateinit var bindings: MutableMap<Class<*>, Constructor<DefaultsSaver>>

@Suppress("UNCHECKED_CAST")
private fun findBindingConstructor(cls: Class<*>): Constructor<DefaultsSaver>? {
    if (!::bindings.isInitialized) {
        bindings = WeakHashMap()
    }
    var binding = bindings[cls]
    if (binding != null) {
        Defaults.debug("HIT: Cache found in binding weak map.")
        return binding
    }
    if (cls.name.startsWith("android.") || cls.name.startsWith("java.")) {
        Defaults.debug("MISS: Reached framework class. Abandoning search.")
        return null
    }
    try {
        binding = cls.classLoader!!
            .loadClass(cls.name + BindDefault.SUFFIX)
            .getConstructor(cls, ReadableDefaults::class.java) as Constructor<DefaultsSaver>
        Defaults.debug("HIT: Loaded binding class, caching in weak map.")
    } catch (e: ClassNotFoundException) {
        val superclass = cls.superclass
        Defaults.debug("Not found. Trying superclass ${superclass!!.name}")
        binding = findBindingConstructor(superclass)
    } catch (e: NoSuchMethodException) {
        throw RuntimeException("Unable to find binding constructor for ${cls.name}", e)
    }
    bindings[cls] = binding!!
    return binding
}