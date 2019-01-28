package com.hendraanggrian.local

import java.io.File
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.util.WeakHashMap

private var debugger: ((String) -> Unit)? = { println(it) }
private lateinit var bindings: MutableMap<Class<*>, Constructor<Saver>>

infix fun LocalSettings<*>.bind(target: Any): Saver {
    val targetClass = target.javaClass
    debugger?.invoke("Looking up binding for ${targetClass.name}")
    val constructor = findBindingConstructor(targetClass)
    if (constructor == null) {
        debugger?.invoke("${targetClass.name} binding not found, returning empty Committer.")
        return Saver.EMPTY
    }
    try {
        return constructor.newInstance(target, this)
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
private fun findBindingConstructor(cls: Class<*>): Constructor<Saver>? {
    if (!::bindings.isInitialized) bindings = WeakHashMap()
    var binding = bindings[cls]
    if (binding != null) {
        debugger?.invoke("HIT: Cache found in binding weak map.")
        return binding
    }
    if (cls.name.startsWith("android.") || cls.name.startsWith("java.")) {
        debugger?.invoke("MISS: Reached framework class. Abandoning search.")
        return null
    }
    try {
        binding = cls.classLoader!!
            .loadClass(cls.name + Local.SUFFIX)
            .getConstructor(cls, File::class.java) as Constructor<Saver>
        debugger?.invoke("HIT: Loaded binding class, caching in weak map.")
    } catch (e: ClassNotFoundException) {
        val superclass = cls.superclass
        debugger?.invoke("Not found. Trying superclass ${superclass!!.name}")
        binding = findBindingConstructor(superclass)
    } catch (e: NoSuchMethodException) {
        throw RuntimeException("Unable to find binding constructor for \$name", e)
    }
    bindings[cls] = binding!!
    return binding
}