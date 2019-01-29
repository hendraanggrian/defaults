package com.hendraanggrian.defaults

import java.lang.reflect.InvocationTargetException

/** Bind fields in target (this) annotated with [Default] from [source]. */
fun Any.bindDefaults(source: Defaults<*>): Defaults.Saver {
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