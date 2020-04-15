package com.hendraanggrian.prefy

import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.util.WeakHashMap

/**
 * Bind fields annotated with [BindPreference] with values from this preferences.
 * @receiver source preferences.
 * @param target fields' owner.
 * @return saver instance to apply changes made to the fields.
 * @throws RuntimeException when constructor of binding class cannot be found.
 */
fun ReadablePreferences.bind(target: Any): PreferencesSaver {
    val targetClass = target.javaClass
    Prefy.info("Looking up binding for ${targetClass.name}")
    val constructor = Prefy.findBindingConstructor(targetClass)
    if (constructor == null) {
        Prefy.info("${targetClass.name} binding not found, returning empty saver.")
        return PreferencesSaver.EMPTY
    }
    try {
        return constructor.newInstance(this, target)
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

/** Create an instance of [WritablePreferences] or [EditablePreferences] and bind its values to JVM fields with this object. */
object Prefy {
    private var BINDINGS: MutableMap<Class<*>, Constructor<PreferencesSaver>>? = null
    private var LOGGER: PreferencesLogger? = null

    /**
     * Set logging behavior using [PreferencesLogger] instance.
     * @param logger custom or pre-determined logger (e.g: [PreferencesLogger.System]).
     */
    fun setLogger(logger: PreferencesLogger) {
        LOGGER = logger
    }

    internal fun info(message: String) {
        LOGGER?.info(message)
    }

    internal fun warn(message: String) {
        LOGGER?.warn(message)
    }

    @Suppress("UNCHECKED_CAST")
    internal fun findBindingConstructor(cls: Class<*>): Constructor<PreferencesSaver>? {
        if (BINDINGS == null) BINDINGS = WeakHashMap()
        var binding = BINDINGS!![cls]
        if (binding != null) {
            info("HIT: Cache found in binding weak map.")
            return binding
        }
        if (cls.name.startsWith("android.") || cls.name.startsWith("java.")) {
            info("MISS: Reached framework class. Abandoning search.")
            return null
        }
        try {
            binding = cls.classLoader!!
                .loadClass("${cls.name}_PreferencesBinding")
                .getConstructor(ReadablePreferences::class.java, cls) as Constructor<PreferencesSaver>
            info("HIT: Loaded binding class, caching in weak map.")
        } catch (e: ClassNotFoundException) {
            val superclass = cls.superclass
            warn("Not found. Trying superclass ${superclass!!.name} ...")
            binding = findBindingConstructor(superclass)
        } catch (e: NoSuchMethodException) {
            throw RuntimeException("Unable to find binding constructor for ${cls.name}", e)
        }
        BINDINGS!![cls] = checkNotNull(binding) {
            "Unable to find preferences binding, is `prefy-compiler` correctly installed?"
        }
        return binding
    }
}
