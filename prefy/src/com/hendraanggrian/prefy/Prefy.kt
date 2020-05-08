package com.hendraanggrian.prefy

import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.util.WeakHashMap

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

    /**
     * Bind fields annotated with [BindPreference] with values from this preferences.
     * @param source preferences to extract.
     * @param target fields' owner.
     * @return saver instance to apply changes made to the fields.
     * @throws RuntimeException when constructor of binding class cannot be found.
     */
    fun bind(source: ReadablePreferences, target: Any): PreferencesSaver {
        val cls = target.javaClass
        val clsName = cls.simpleName
        info("Looking up binding for $clsName")
        val constructor = findBindingConstructor(cls)
        if (constructor == null) {
            info("$clsName binding not found, returning empty saver.")
            return PreferencesSaver.EMPTY
        }
        try {
            return constructor.newInstance(source, target)
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

    @Suppress("UNCHECKED_CAST")
    private fun findBindingConstructor(cls: Class<*>): Constructor<PreferencesSaver>? {
        if (BINDINGS == null) {
            BINDINGS = WeakHashMap()
        }
        var binding = BINDINGS!![cls]
        if (binding != null) {
            info("HIT: Cache found in binding weak map.")
            return binding
        }
        val clsName = cls.simpleName
        if (clsName.startsWith("android.") || clsName.startsWith("java.")) {
            info("MISS: Reached framework class. Abandoning search.")
            return null
        }
        try {
            binding = cls.classLoader!!
                .loadClass(clsName + BindPreference.SUFFIX)
                .getConstructor(ReadablePreferences::class.java, cls) as Constructor<PreferencesSaver>
            info("HIT: Loaded binding class, caching in weak map.")
        } catch (e: ClassNotFoundException) {
            val supercls = cls.superclass!!
            warn("Not found. Trying superclass ${supercls.simpleName} ...")
            binding = findBindingConstructor(supercls)
        } catch (e: NoSuchMethodException) {
            throw RuntimeException("Unable to find binding constructor for $clsName", e)
        }
        BINDINGS!![cls] = checkNotNull(binding) {
            "Unable to find preferences binding, is `prefy-compiler` correctly installed?"
        }
        return binding
    }
}
