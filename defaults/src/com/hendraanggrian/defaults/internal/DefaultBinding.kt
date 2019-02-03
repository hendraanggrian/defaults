package com.hendraanggrian.defaults.internal

import com.hendraanggrian.defaults.Defaults
import com.hendraanggrian.defaults.ReadableDefaults

/** Internal component, keep out. */
abstract class DefaultBinding(protected val source: ReadableDefaults) : Defaults.Saver {

    protected fun get(key: String, defaultValue: String?): String? =
        source[key] ?: defaultValue

    protected fun get(key: String, defaultValue: Int): Int =
        source.getInt(key) ?: defaultValue

    protected fun get(key: String, defaultValue: Long): Long =
        source.getLong(key) ?: defaultValue

    protected fun get(key: String, defaultValue: Float): Float =
        source.getFloat(key) ?: defaultValue

    protected fun get(key: String, defaultValue: Boolean): Boolean =
        source.getBoolean(key) ?: defaultValue

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