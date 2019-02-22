package com.hendraanggrian.defaults.internal

import com.hendraanggrian.defaults.DefaultsEditor

/** Provides support to write boolean as string. */
interface BooleanDefaultsEditorSupport : DefaultsEditor {
    override fun set(key: String, value: Boolean) = set(key, value.toString())
}

/** Provides support to write double as string. */
interface DoubleDefaultsEditorSupport : DefaultsEditor {
    override fun set(key: String, value: Double) = set(key, value.toString())
}

/** Provides support to write float as string. */
interface FloatDefaultsEditorSupport : DefaultsEditor {
    override fun set(key: String, value: Float) = set(key, value.toString())
}

/** Provides support to write long as string. */
interface LongDefaultsEditorSupport : DefaultsEditor {
    override fun set(key: String, value: Long) = set(key, value.toString())
}

/** Provides support to write int as string. */
interface IntDefaultsEditorSupport : DefaultsEditor {
    override fun set(key: String, value: Int) = set(key, value.toString())
}

/** Provides support to write short as string. */
interface ShortDefaultsEditorSupport : DefaultsEditor {
    override fun set(key: String, value: Short) = set(key, value.toString())
}

/** Provides support to write byte as string. */
interface ByteDefaultsEditorSupport : DefaultsEditor {
    override fun set(key: String, value: Byte) = set(key, value.toString())
}