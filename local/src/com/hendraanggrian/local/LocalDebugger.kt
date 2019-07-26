package com.hendraanggrian.local

/** Represents a runnable with string param. */
open class LocalDebugger(debug: (String) -> Unit) : ((String) -> Unit) by debug {

    /** Features often use this companion as receiver. */
    companion object {

        /** Default debugger, which just prints in system. */
        val System: LocalDebugger get() = LocalDebugger { println(it) }
    }
}
