package local

import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.util.WeakHashMap

object Local {

    private var logger: LocalLogger? = null
    private lateinit var bindings: MutableMap<Class<*>, Constructor<LocalSaver>>

    fun setLogger(logger: LocalLogger) {
        this.logger = logger
    }

    internal fun log(message: String) {
        logger?.invoke(message)
    }

    /** Bind fields in target (this) annotated with [BindLocal] from this local. */
    fun bind(source: ReadableLocal, target: Any): LocalSaver {
        val targetClass = target.javaClass
        log("Looking up binding for ${targetClass.name}")
        val constructor = findBindingConstructor(targetClass)
        if (constructor == null) {
            log("${targetClass.name} binding not found, returning empty saver.")
            return LocalSaver.EMPTY
        }
        try {
            return constructor.newInstance(target, source)
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
    private fun findBindingConstructor(cls: Class<*>): Constructor<LocalSaver>? {
        if (!::bindings.isInitialized) {
            bindings = WeakHashMap()
        }
        var binding = bindings[cls]
        if (binding != null) {
            log("HIT: Cache found in binding weak map.")
            return binding
        }
        if (cls.name.startsWith("android.") || cls.name.startsWith("java.")) {
            log("MISS: Reached framework class. Abandoning search.")
            return null
        }
        try {
            binding = cls.classLoader!!
                .loadClass(cls.name + BindLocal.SUFFIX)
                .getConstructor(cls, ReadableLocal::class.java) as Constructor<LocalSaver>
            log("HIT: Loaded binding class, caching in weak map.")
        } catch (e: ClassNotFoundException) {
            val superclass = cls.superclass
            log("Not found. Trying superclass ${superclass!!.name}")
            binding = findBindingConstructor(superclass)
        } catch (e: NoSuchMethodException) {
            throw RuntimeException("Unable to find binding constructor for ${cls.name}", e)
        }
        bindings[cls] = binding!!
        return binding
    }
}
