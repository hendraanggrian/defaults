package com.hendraanggrian.prefy

/** Interface that determines preferences logging behavior. */
interface PreferencesLogger {

    companion object {
        /** Logger that prints to [System.out] with type prefix. */
        val System: PreferencesLogger
            get() = object : PreferencesLogger {
                override fun info(message: String) = println("INFO: $message")
                override fun warn(message: String) = println("WARN: $message")
            }
    }

    /**
     * Log message in information channel.
     * @param message text to print.
     */
    fun info(message: String)

    /**
     * Log message in warning channel.
     * @param message text to print.
     */
    fun warn(message: String)
}
