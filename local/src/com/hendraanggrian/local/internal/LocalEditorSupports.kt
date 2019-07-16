package com.hendraanggrian.local.internal

import com.hendraanggrian.local.Local

/** Provides support to write boolean as string. */
interface BooleanLocalEditorSupport : Local.Editor {
    override fun set(key: String, value: Boolean) = set(key, value.toString())
}

/** Provides support to write double as string. */
interface DoubleLocalEditorSupport : Local.Editor {
    override fun set(key: String, value: Double) = set(key, value.toString())
}

/** Provides support to write float as string. */
interface FloatLocalEditorSupport : Local.Editor {
    override fun set(key: String, value: Float) = set(key, value.toString())
}

/** Provides support to write long as string. */
interface LongLocalEditorSupport : Local.Editor {
    override fun set(key: String, value: Long) = set(key, value.toString())
}

/** Provides support to write int as string. */
interface IntLocalEditorSupport : Local.Editor {
    override fun set(key: String, value: Int) = set(key, value.toString())
}

/** Provides support to write short as string. */
interface ShortLocalEditorSupport : Local.Editor {
    override fun set(key: String, value: Short) = set(key, value.toString())
}

/** Provides support to write byte as string. */
interface ByteLocalEditorSupport : Local.Editor {
    override fun set(key: String, value: Byte) = set(key, value.toString())
}
