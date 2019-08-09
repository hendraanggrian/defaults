package local

/** Settings value modifier with Kotlin operators. */
interface LocalEditor : LocalSaver {

    /** Removes a setting. */
    fun remove(key: String)

    /** Removes all settings. */
    fun clear()

    /** Add/change string value. */
    operator fun set(key: String, value: String)

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

    /** Convenient method to quickly open an editor and apply changes in dsl. */
    operator fun invoke(edit: LocalEditor.() -> Unit): LocalEditor = also {
        edit(it)
        it.saveAsync()
    }
}
