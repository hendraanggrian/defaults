package com.hendraanggrian.defaults

import androidx.annotation.AnyThread
import androidx.annotation.WorkerThread
import java.lang.ref.WeakReference
import java.lang.reflect.Constructor
import java.util.WeakHashMap

/**
 * Represents a set of key-value pairs used as local settings.
 *
 * @param E local settings editor
 */
interface Defaults<E : Defaults.Editor> {

    /** Often used and extended to create [Defaults] instance from ranges of input. */
    companion object {

        const val TAG = "com.hendraanggrian.defaults.Defaults"

        internal var DEBUGGER: DefaultsDebugger? = null
        private lateinit var BINDINGS: MutableMap<Class<*>, Constructor<Saver>>

        /** Modify debugging behavior, default is none. */
        fun setDebug(debug: DefaultsDebugger?) {
            DEBUGGER = debug
        }

        @Suppress("UNCHECKED_CAST")
        internal fun findBindingConstructor(cls: Class<*>): Constructor<Saver>? {
            if (!Companion::BINDINGS.isInitialized) BINDINGS = WeakHashMap()
            var binding = BINDINGS[cls]
            if (binding != null) {
                DEBUGGER?.invoke("HIT: Cache found in binding weak map.")
                return binding
            }
            if (cls.name.startsWith("android.") || cls.name.startsWith("java.")) {
                DEBUGGER?.invoke("MISS: Reached framework class. Abandoning search.")
                return null
            }
            try {
                binding = cls.classLoader!!
                    .loadClass(cls.name + BindDefault.SUFFIX)
                    .getConstructor(cls, Defaults::class.java) as Constructor<Saver>
                DEBUGGER?.invoke("HIT: Loaded binding class, caching in weak map.")
            } catch (e: ClassNotFoundException) {
                val superclass = cls.superclass
                DEBUGGER?.invoke("Not found. Trying superclass ${superclass!!.name}")
                binding =
                    findBindingConstructor(
                        superclass
                    )
            } catch (e: NoSuchMethodException) {
                throw RuntimeException("Unable to find binding constructor for \$name", e)
            }
            BINDINGS[cls] = binding!!
            return binding
        }
    }

    /** Checks if a setting exists. */
    operator fun contains(key: String): Boolean

    /** Returns non-null string value. */
    operator fun get(key: String): String? = get(key)

    fun get(key: String, def: String?): String?

    fun getBoolean(key: String): Boolean

    fun getBoolean(key: String, def: Boolean): Boolean

    fun getDouble(key: String): Double

    fun getDouble(key: String, def: Double): Double

    fun getFloat(key: String): Float

    fun getFloat(key: String, def: Float): Float

    fun getLong(key: String): Long

    fun getLong(key: String, def: Long): Long

    fun getInt(key: String): Int

    fun getInt(key: String, def: Int): Int

    fun getShort(key: String): Short

    fun getShort(key: String, def: Short): Short

    fun getByte(key: String): Byte

    fun getByte(key: String, def: Byte): Byte

    /**
     * When editor instance is created, resources must be available (e.g.: opening sql transaction).
     * Resources may be released upon `save` or `saveAsync`.
     */
    fun getEditor(): E

    /**
     * Convenient method to quickly open an editor and apply changes in dsl.
     *
     * @param edit receiver is [Defaults] for access to settings' contents, next param is [Editor]
     *        for custom editing.
     */
    infix operator fun invoke(edit: (Defaults<E>.(E) -> Unit)): Defaults<E> =
        apply { getEditor().also { edit(it) }.save() }

    /** Base interface to save changes to local settings. */
    interface Saver {

        companion object {
            internal val EMPTY: Saver = object : Saver {
                override fun save() {}
                override fun saveAsync() {}
            }
        }

        /** Non-blocking save in the background. */
        @WorkerThread
        fun save()

        /** Blocking save. */
        @AnyThread
        fun saveAsync()
    }

    /** Responsible of modifying settings. */
    interface Editor : Saver {

        /** Removes a setting. */
        operator fun minusAssign(key: String)

        /** Removes all settings. */
        fun reset()

        /** Add/change string value. */
        operator fun set(key: String, value: String?)

        /** Add/change boolean value. */
        operator fun set(key: String, value: Boolean)

        /** Add/change double value. */
        operator fun set(key: String, value: Double)

        /** Add/change float value. */
        operator fun set(key: String, value: Float)

        /** Add/change long value. */
        operator fun set(key: String, value: Long)

        /** Add/change int value. */
        operator fun set(key: String, value: Int)

        /** Add/change short value. */
        operator fun set(key: String, value: Short)

        /** Add/change byte value. */
        operator fun set(key: String, value: Byte)
    }

    /**
     * Represents defaults that can directly write values without editor being created,
     * though editor works and behaves like the defaults itself.
     */
    abstract class NoEditor : Defaults<NoEditor.FakeEditor>, Defaults.Editor {
        private var editorRef: WeakReference<FakeEditor?> = WeakReference(null)

        override fun getEditor(): FakeEditor {
            var editor = editorRef.get()
            if (editor == null) {
                editor = FakeEditor()
                editorRef = WeakReference(editor)
            }
            return editor
        }

        inner class FakeEditor : Defaults.Editor by this
    }
}