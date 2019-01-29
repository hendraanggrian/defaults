package com.hendraanggrian.local

import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.util.WeakHashMap

interface LocalSettings<E : LocalSettings.Editor> {

    companion object {
        internal var debugger: ((String) -> Unit)? = { println(it) }
        private lateinit var bindings: MutableMap<Class<*>, Constructor<Saver>>

        @Suppress("UNCHECKED_CAST")
        internal fun findBindingConstructor(cls: Class<*>): Constructor<Saver>? {
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
                    .loadClass(cls.name + BindLocal.SUFFIX)
                    .getConstructor(cls, LocalSettings::class.java) as Constructor<Saver>
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
    }

    operator fun contains(key: String): Boolean

    operator fun get(key: String): String = getString(key)!!

    fun getString(key: String): String?

    fun getString(key: String, defaultValue: String?): String?

    fun getInt(key: String): Int

    fun getInt(key: String, defaultValue: Int): Int

    fun getLong(key: String): Long

    fun getLong(key: String, defaultValue: Long): Long

    fun getFloat(key: String): Float

    fun getFloat(key: String, defaultValue: Float): Float

    fun getBoolean(key: String): Boolean

    fun getBoolean(key: String, defaultValue: Boolean): Boolean

    fun getEditor(): E

    fun edit(edit: (E.() -> Unit)) = getEditor().apply { edit() }.save()

    interface Editor : Saver {

        operator fun minusAssign(key: String)

        fun reset()

        operator fun set(key: String, value: String?)

        operator fun set(key: String, value: Int)

        operator fun set(key: String, value: Long)

        operator fun set(key: String, value: Float)

        operator fun set(key: String, value: Boolean)
    }

    interface Saver {

        fun save()

        fun saveAsync()

        companion object {

            val EMPTY: Saver = object : Saver {
                override fun save() {}

                override fun saveAsync() {}
            }
        }
    }
}

infix fun Any.bindLocal(source: LocalSettings<*>): LocalSettings.Saver {
    val targetClass = javaClass
    LocalSettings.debugger?.invoke("Looking up binding for ${targetClass.name}")
    val constructor = LocalSettings.findBindingConstructor(targetClass)
    if (constructor == null) {
        LocalSettings.debugger?.invoke("${targetClass.name} binding not found, returning empty Committer.")
        return LocalSettings.Saver.EMPTY
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