package com.hendraanggrian.prefs

import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.util.WeakHashMap

/**
 * Bind fields annotated with [BindPref] with values from this preferences.
 * @receiver source preferences.
 * @param target fields' owner.
 * @return saver instance to apply changes made to the fields.
 * @throws RuntimeException when constructor of binding class cannot be found.
 */
fun ReadablePrefs.bind(target: Any): Prefs.Saver {
    val targetClass = target.javaClass
    Prefs.info("Looking up binding for ${targetClass.name}")
    val constructor = Prefs.findBindingConstructor(targetClass)
    if (constructor == null) {
        Prefs.info("${targetClass.name} binding not found, returning empty saver.")
        return EMPTY_SAVER
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

private val EMPTY_SAVER: Prefs.Saver = object : Prefs.Saver {
    override fun save() = Prefs.warn("Prefs.Saver: Saving an empty instance")
}

/** Create an instance of [WritablePrefs] or [EditablePrefs] and bind its values to JVM fields with this object. */
object Prefs {
    private var BINDINGS: MutableMap<Class<*>, Constructor<Saver>>? = null
    private var LOGGER: Logger? = null

    /**
     * Set logging behavior using [Logger] instance.
     * @param logger custom or pre-determined logger (e.g: [Logger.System]).
     */
    fun setLogger(logger: Logger) {
        LOGGER = logger
    }

    internal fun info(message: String) {
        LOGGER?.info(message)
    }

    internal fun warn(message: String) {
        LOGGER?.warn(message)
    }

    @Suppress("UNCHECKED_CAST")
    internal fun findBindingConstructor(cls: Class<*>): Constructor<Saver>? {
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
                .loadClass("${cls.name}_PrefsBinding")
                .getConstructor(ReadablePrefs::class.java, cls) as Constructor<Saver>
            info("HIT: Loaded binding class, caching in weak map.")
        } catch (e: ClassNotFoundException) {
            val superclass = cls.superclass
            warn("Not found. Trying superclass ${superclass!!.name} ...")
            binding = findBindingConstructor(superclass)
        } catch (e: NoSuchMethodException) {
            throw RuntimeException("Unable to find binding constructor for ${cls.name}", e)
        }
        BINDINGS!![cls] = binding!!
        return binding
    }

    /**
     * Interface used for modifying values in a [ReadablePrefs] object.
     * All changes you make in an editor are batched,
     * and not copied back to the original [ReadablePrefs] until you call [save].
     */
    interface Editor : Saver {

        /**
         * Mark in the editor that a preference value should be removed.
         * @param key name of the preference to remove.
         */
        fun remove(key: String)

        /** Mark in the editor to remove *all* values from the preferences. */
        fun clear()

        /**
         * Set a [String] value in the preferences editor.
         * @param key name of the preference to modify.
         * @param value new value for the preference. Passing `null` is equivalent to [remove].
         */
        operator fun set(key: String, value: String?)

        /**
         * Set a [Boolean] value in the preferences editor.
         * @param key name of the preference to modify.
         * @param value new value for the preference. Passing `null` is equivalent to [remove].
         * @throws UnsupportedOperationException if [Boolean] is not a supported type of this preferences.
         */
        operator fun set(key: String, value: Boolean)

        /**
         * Set a [Double] value in the preferences editor.
         * @param key name of the preference to modify.
         * @param value new value for the preference. Passing `null` is equivalent to [remove].
         * @throws UnsupportedOperationException if [Double] is not a supported type of this preferences.
         */
        operator fun set(key: String, value: Double)

        /**
         * Set a [Float] value in the preferences editor.
         * @param key name of the preference to modify.
         * @param value new value for the preference. Passing `null` is equivalent to [remove].
         * @throws UnsupportedOperationException if [Float] is not a supported type of this preferences.
         */
        operator fun set(key: String, value: Float)

        /**
         * Set a [Long] value in the preferences editor.
         * @param key name of the preference to modify.
         * @param value new value for the preference. Passing `null` is equivalent to [remove].
         * @throws UnsupportedOperationException if [Long] is not a supported type of this preferences.
         */
        operator fun set(key: String, value: Long)

        /**
         * Set a [Int] value in the preferences editor.
         * @param key name of the preference to modify.
         * @param value new value for the preference. Passing `null` is equivalent to [remove].
         * @throws UnsupportedOperationException if [Int] is not a supported type of this preferences.
         */
        operator fun set(key: String, value: Int)

        /**
         * Set a [Short] value in the preferences editor.
         * @param key name of the preference to modify.
         * @param value new value for the preference. Passing `null` is equivalent to [remove].
         * @throws UnsupportedOperationException if [Short] is not a supported type of this preferences.
         */
        operator fun set(key: String, value: Short)

        /**
         * Set a [Byte] value in the preferences editor.
         * @param key name of the preference to modify.
         * @param value new value for the preference. Passing `null` is equivalent to [remove].
         * @throws UnsupportedOperationException if [Byte] is not a supported type of this preferences.
         */
        operator fun set(key: String, value: Byte)
    }

    /**
     * Interface used for applying changes made in [Editor] object.
     * When using [ReadablePrefs.bind] to inject preferences' values to JVM fields,
     * [Saver] is returned back to save changes made in JVM fields.
     */
    interface Saver {

        /**
         * Commit your preferences changes back from [Editor] to the [ReadablePrefs] object it is editing.
         * This atomically performs the requested modifications, replacing whatever is currently in the [ReadablePrefs].
         */
        fun save()
    }

    /** Interface that determines preferences logging behavior. */
    interface Logger {
        companion object {
            /** Logger that prints to [System.out] with type prefix. */
            val System: Logger
                get() = object : Logger {
                    override fun info(message: String) = println("INFO: $message")
                    override fun warn(message: String) = println("WARN: $message")
                }
        }

        /**
         * Log message in information channel.
         * @param message text to print.
         */
        fun info(message: String)

        /**
         * Log message in warning channel.
         * @param message text to print.
         */
        fun warn(message: String)
    }
}
