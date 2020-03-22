package com.hendraanggrian.prefs

import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.util.WeakHashMap

/**
 * Interface for accessing and modifying preference data on any target platform (JVM, Android, etc.).
 *
 * @see SimplePrefs
 * @see EditablePrefs
 */
interface Prefs {

    companion object {
        private val EMPTY_SAVER: Saver = object : Saver {
            override fun save() = warn("Prefs.Saver: Saving an empty instance")
        }
        internal lateinit var BINDINGS: MutableMap<Class<*>, Constructor<Saver>>
        internal lateinit var LOGGER: Logger

        /**
         * Set logging behavior using [Logger] instance.
         * @param logger custom or pre-determined logger (e.g: [Logger.System]).
         */
        fun setLogger(logger: Logger) {
            LOGGER = logger
        }

        internal fun info(message: String) {
            if (::LOGGER.isInitialized) LOGGER.info(message)
        }

        internal fun warn(message: String) {
            if (::LOGGER.isInitialized) LOGGER.warn(message)
        }

        @Suppress("UNCHECKED_CAST")
        private fun findBindingConstructor(cls: Class<*>): Constructor<Saver>? {
            if (!::BINDINGS.isInitialized) BINDINGS = WeakHashMap()
            var binding = BINDINGS[cls]
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
                    .getConstructor(Prefs::class.java, cls) as Constructor<Saver>
                info("HIT: Loaded binding class, caching in weak map.")
            } catch (e: ClassNotFoundException) {
                val superclass = cls.superclass
                warn("Not found. Trying superclass ${superclass!!.name} ...")
                binding = findBindingConstructor(superclass)
            } catch (e: NoSuchMethodException) {
                throw RuntimeException("Unable to find binding constructor for ${cls.name}", e)
            }
            BINDINGS[cls] = binding!!
            return binding
        }
    }

    /**
     * Checks whether the preferences contains a preference.
     * @param key name of the preference to check.
     * @return true if the preference exists in the preferences, otherwise false.
     */
    operator fun contains(key: String): Boolean

    /**
     * Retrieve a [String] value from the preferences.
     * @param key name of the preference to retrieve.
     * @return preference value if it exists, or null.
     */
    operator fun get(key: String): String?

    /**
     * Retrieve a [String] value from the preferences, providing default value as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     */
    fun getOrDefault(key: String, defaultValue: String): String =
        findValue(key, defaultValue) { get(it) }

    /**
     * Retrieve a [String] value from the preferences, calling default value getter as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     */
    fun getOrElse(key: String, defaultValue: () -> String): String =
        getOrDefault(key, defaultValue())

    /**
     * Retrieve a [Boolean] value from the preferences.
     * @param key name of the preference to retrieve.
     * @return preference value if it exists, or null.
     * @throws UnsupportedOperationException if [Boolean] is not a supported type of this preferences.
     */
    fun getBoolean(key: String): Boolean?

    /**
     * Retrieve a [Boolean] value from the preferences, providing default value as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if [Boolean] is not a supported type of this preferences.
     */
    fun getBooleanOrDefault(key: String, defaultValue: Boolean): Boolean =
        findValue(key, defaultValue) { getBoolean(it) }

    /**
     * Retrieve a [Boolean] value from the preferences, calling default value getter as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if [Boolean] is not a supported type of this preferences.
     */
    fun getBooleanOrElse(key: String, defaultValue: () -> Boolean): Boolean =
        getBooleanOrDefault(key, defaultValue())

    /**
     * Retrieve a [Double] value from the preferences.
     * @param key name of the preference to retrieve.
     * @return preference value if it exists, or null.
     * @throws UnsupportedOperationException if [Double] is not a supported type of this preferences.
     */
    fun getDouble(key: String): Double?

    /**
     * Retrieve a [Double] value from the preferences, providing default value as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if [Double] is not a supported type of this preferences.
     */
    fun getDoubleOrDefault(key: String, defaultValue: Double): Double =
        findValue(key, defaultValue) { getDouble(it) }

    /**
     * Retrieve a [Double] value from the preferences, calling default value getter as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if [Double] is not a supported type of this preferences.
     */
    fun getDoubleOrElse(key: String, defaultValue: () -> Double): Double =
        getDoubleOrDefault(key, defaultValue())

    /**
     * Retrieve a [Float] value from the preferences.
     * @param key name of the preference to retrieve.
     * @return preference value if it exists, or null.
     * @throws UnsupportedOperationException if [Float] is not a supported type of this preferences.
     */
    fun getFloat(key: String): Float?

    /**
     * Retrieve a [Float] value from the preferences, providing default value as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if [Float] is not a supported type of this preferences.
     */
    fun getFloatOrDefault(key: String, defaultValue: Float): Float =
        findValue(key, defaultValue) { getFloat(it) }

    /**
     * Retrieve a [Float] value from the preferences, calling default value getter as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if [Float] is not a supported type of this preferences.
     */
    fun getFloatOrElse(key: String, defaultValue: () -> Float): Float =
        getFloatOrDefault(key, defaultValue())

    /**
     * Retrieve a [Long] value from the preferences.
     * @param key name of the preference to retrieve.
     * @return preference value if it exists, or null.
     * @throws UnsupportedOperationException if [Long] is not a supported type of this preferences.
     */
    fun getLong(key: String): Long?

    /**
     * Retrieve a [Long] value from the preferences, providing default value as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if [Long] is not a supported type of this preferences.
     */
    fun getLongOrDefault(key: String, defaultValue: Long): Long =
        findValue(key, defaultValue) { getLong(it) }

    /**
     * Retrieve a [Long] value from the preferences, calling default value getter as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if [Long] is not a supported type of this preferences.
     */
    fun getLongOrElse(key: String, defaultValue: () -> Long): Long =
        getLongOrDefault(key, defaultValue())

    /**
     * Retrieve an [Int] value from the preferences.
     * @param key name of the preference to retrieve.
     * @return preference value if it exists, or null.
     * @throws UnsupportedOperationException if [Int] is not a supported type of this preferences.
     */
    fun getInt(key: String): Int?

    /**
     * Retrieve an [Int] value from the preferences, providing default value as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if [Int] is not a supported type of this preferences.
     */
    fun getIntOrDefault(key: String, defaultValue: Int): Int =
        findValue(key, defaultValue) { getInt(it) }

    /**
     * Retrieve an [Int] value from the preferences, calling default value getter as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if [Int] is not a supported type of this preferences.
     */
    fun getIntOrElse(key: String, defaultValue: () -> Int): Int =
        getIntOrDefault(key, defaultValue())

    /**
     * Retrieve a [Short] value from the preferences.
     * @param key name of the preference to retrieve.
     * @return preference value if it exists, or null.
     * @throws UnsupportedOperationException if [Short] is not a supported type of this preferences.
     */
    fun getShort(key: String): Short?

    /**
     * Retrieve a [Short] value from the preferences, providing default value as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if [Short] is not a supported type of this preferences.
     */
    fun getShortOrDefault(key: String, defaultValue: Short): Short =
        findValue(key, defaultValue) { getShort(it) }

    /**
     * Retrieve a [Short] value from the preferences, calling default value getter as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if [Short] is not a supported type of this preferences.
     */
    fun getShortOrElse(key: String, defaultValue: () -> Short): Short =
        getShortOrDefault(key, defaultValue())

    /**
     * Retrieve a [Byte] value from the preferences.
     * @param key name of the preference to retrieve.
     * @return preference value if it exists, or null.
     * @throws UnsupportedOperationException if [Byte] is not a supported type of this preferences.
     */
    fun getByte(key: String): Byte?

    /**
     * Retrieve a [Byte] value from the preferences, providing default value as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if [Byte] is not a supported type of this preferences.
     */
    fun getByteOrDefault(key: String, defaultValue: Byte): Byte =
        findValue(key, defaultValue) { getByte(it) }

    /**
     * Retrieve a [Byte] value from the preferences, calling default value getter as a fallback.
     * @param key name of the preference to retrieve.
     * @param defaultValue value to return if this preference does not exist.
     * @return preference value if it exists, or default value.
     * @throws UnsupportedOperationException if [Byte] is not a supported type of this preferences.
     */
    fun getByteOrElse(key: String, defaultValue: () -> Byte): Byte =
        getByteOrDefault(key, defaultValue())

    /**
     * Bind fields annotated with [BindPref] with values from this preferences.
     * @param target fields' owner.
     * @return saver instance to apply changes made to the fields.
     * @throws RuntimeException when constructor of binding class cannot be found.
     */
    fun bind(target: Any): Saver {
        val targetClass = target.javaClass
        info("Looking up binding for ${targetClass.name}")
        val constructor = findBindingConstructor(targetClass)
        if (constructor == null) {
            info("${targetClass.name} binding not found, returning empty saver.")
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

    private inline fun <T> findValue(key: String, defaultValue: T, getValue: (key: String) -> T?): T {
        if (key !in this) {
            val value = getValue(key)
            if (value != null) {
                return value
            }
        }
        return defaultValue
    }

    /**
     * Interface used for modifying values in a [Prefs] object.
     * All changes you make in an editor are batched,
     * and not copied back to the original [Prefs] until you call [save].
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
     * When using [bind] to inject preferences' values to JVM fields,
     * [Saver] is returned back to save changes made in JVM fields.
     */
    interface Saver {

        /**
         * Commit your preferences changes back from [Editor] to the [Prefs] object it is editing.
         * This atomically performs the requested modifications, replacing whatever is currently in the [Prefs].
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
