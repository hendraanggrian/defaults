package com.hendraanggrian.lokal

/** Represents a runnable with string param. */
open class LokalDebugger(debug: (String) -> Unit) : ((String) -> Unit) by debug {

    /** Features often use this companion as receiver. */
    companion object {

        /** Default debugger, which just prints in system. */
        val Default: LokalDebugger get() = LokalDebugger { println(it) }
    }
}