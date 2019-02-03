package com.hendraanggrian.defaults

/** Represents a runnable with string param. */
open class DefaultsDebugger(debug: (String) -> Unit) : ((String) -> Unit) by debug {

    /** Features often use this companion as receiver. */
    companion object {

        /** BindDefault debugger, which just prints in system. */
        val Default: DefaultsDebugger get() = DefaultsDebugger { println(it) }
    }
}