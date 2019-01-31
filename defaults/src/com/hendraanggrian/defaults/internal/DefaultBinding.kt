package com.hendraanggrian.defaults.internal

import com.hendraanggrian.defaults.Defaults
import com.hendraanggrian.defaults.ReadableDefaults

/** Internal component, keep out. */
abstract class DefaultBinding(protected val source: ReadableDefaults) : Defaults.Saver {

    protected fun get(key: String, def: String?): String? = source.get(key, def)

    protected fun get(key: String, def: Int): Int = source.getInt(key, def)

    protected fun get(key: String, def: Long): Long = source.getLong(key, def)

    protected fun get(key: String, def: Float): Float = source.getFloat(key, def)

    protected fun get(key: String, def: Boolean): Boolean = source.getBoolean(key, def)

    /**
     * Get editor instance from source defaults. Some defaults may need to open instance, while
     * simple defaults already implements editor
     *
     * @throws IllegalStateException unsupported behavior, contact `github.com/hendraanggrian/defaults/issues`
     */
    protected fun getEditor(): Defaults.Editor = when (source) {
        is Defaults<*> -> source.getEditor()
        is Defaults.Editor -> source //
        else -> throw IllegalStateException()
    }

    protected fun set(editor: Defaults.Editor, key: String, value: String?) {
        editor[key] = value
    }

    protected fun set(editor: Defaults.Editor, key: String, value: Int) {
        editor[key] = value
    }

    protected fun set(editor: Defaults.Editor, key: String, value: Long) {
        editor[key] = value
    }

    protected fun set(editor: Defaults.Editor, key: String, value: Float) {
        editor[key] = value
    }

    protected fun set(editor: Defaults.Editor, key: String, value: Boolean) {
        editor[key] = value
    }
}