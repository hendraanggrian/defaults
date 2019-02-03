package com.hendraanggrian.defaults

/** Settings value modifier with Kotlin operators. */
interface DefaultsEditor : DefaultsSaver {

    /** Removes a setting. */
    operator fun minusAssign(key: String)

    /** Removes all settings. */
    fun clear()

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