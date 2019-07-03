@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.lokal

import java.io.File
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.util.WeakHashMap
import java.util.prefs.Preferences

/** A set of readable local settings, modify value with editor created with [getEditor]. */
interface Lokal<E : LokalEditor> : ReadableLokal {

    companion object {
        private var debugger: LokalDebugger? = null

        /** Modify debugging behavior, default is null. */
        fun setDebugger(debug: LokalDebugger?) {
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
}

/** Creates lokal instance from file. */
inline fun File.toLokal(): PropertiesLokal = PropertiesLokal(this)

/** Creates lokal instance from file. */
inline fun File.bindLokal(target: Any): LokalSaver = toLokal().bindLokal(target)

/** Creates lokal instance from preferences. */
inline fun Preferences.toLokal(): PreferencesLokal = PreferencesLokal(this)

/** Creates lokal instance from file. */
inline fun Preferences.bindLokal(target: Any): LokalSaver =
    toLokal().bindLokal(target)

/** Bind fields in target (this) annotated with [BindLokal] from this lokal. */
fun ReadableLokal.bindLokal(target: Any): LokalSaver {
    val targetClass = target.javaClass
    Lokal.debug("Looking up binding for ${targetClass.name}")
    val constructor = findBindingConstructor(targetClass)
    if (constructor == null) {
        Lokal.debug("${targetClass.name} binding not found, returning empty saver.")
        return LokalSaver.EMPTY
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

private lateinit var bindings: MutableMap<Class<*>, Constructor<LokalSaver>>

@Suppress("UNCHECKED_CAST")
private fun findBindingConstructor(cls: Class<*>): Constructor<LokalSaver>? {
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
            .getConstructor(cls, ReadableLokal::class.java) as Constructor<LokalSaver>
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