package com.hendraanggrian.lokal.internal

import com.hendraanggrian.lokal.LokalEditor

/** Provides support to write boolean as string. */
interface BooleanLokalEditorSupport : LokalEditor {
    override fun set(key: String, value: Boolean) = set(key, value.toString())
}

/** Provides support to write double as string. */
interface DoubleLokalEditorSupport : LokalEditor {
    override fun set(key: String, value: Double) = set(key, value.toString())
}

/** Provides support to write float as string. */
interface FloatLokalEditorSupport : LokalEditor {
    override fun set(key: String, value: Float) = set(key, value.toString())
}

/** Provides support to write long as string. */
interface LongLokalEditorSupport : LokalEditor {
    override fun set(key: String, value: Long) = set(key, value.toString())
}

/** Provides support to write int as string. */
interface IntLokalEditorSupport : LokalEditor {
    override fun set(key: String, value: Int) = set(key, value.toString())
}

/** Provides support to write short as string. */
interface ShortLokalEditorSupport : LokalEditor {
    override fun set(key: String, value: Short) = set(key, value.toString())
}

/** Provides support to write byte as string. */
interface ByteLokalEditorSupport : LokalEditor {
    override fun set(key: String, value: Byte) = set(key, value.toString())
}