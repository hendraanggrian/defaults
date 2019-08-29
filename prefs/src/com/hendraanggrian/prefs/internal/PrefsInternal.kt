package com.hendraanggrian.prefs.internal

object PrefsInternal {
    private var debug: ((String) -> Unit)? = null

    internal fun debug(message: String) {
        debug?.invoke(message)
    }

    fun setDebugInternal(isDebug: Boolean, action: (String) -> Unit) {
        debug = when {
            isDebug -> null
            else -> action
        }
    }
}
