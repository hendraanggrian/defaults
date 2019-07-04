package com.hendraanggrian.lokal.internal

import com.hendraanggrian.lokal.Lokal

/** Provides support to write boolean as string. */
interface BooleanLokalEditorSupport : Lokal.Editor {
    override fun set(key: String, value: Boolean) = set(key, value.toString())
}

/** Provides support to write double as string. */
interface DoubleLokalEditorSupport : Lokal.Editor {
    override fun set(key: String, value: Double) = set(key, value.toString())
}

/** Provides support to write float as string. */
interface FloatLokalEditorSupport : Lokal.Editor {
    override fun set(key: String, value: Float) = set(key, value.toString())
}

/** Provides support to write long as string. */
interface LongLokalEditorSupport : Lokal.Editor {
    override fun set(key: String, value: Long) = set(key, value.toString())
}

/** Provides support to write int as string. */
interface IntLokalEditorSupport : Lokal.Editor {
    override fun set(key: String, value: Int) = set(key, value.toString())
}

/** Provides support to write short as string. */
interface ShortLokalEditorSupport : Lokal.Editor {
    override fun set(key: String, value: Short) = set(key, value.toString())
}

/** Provides support to write byte as string. */
interface ByteLokalEditorSupport : Lokal.Editor {
    override fun set(key: String, value: Byte) = set(key, value.toString())
}