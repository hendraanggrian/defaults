package com.hendraanggrian.defaults

open class DefaultsDebugger(debug: (String) -> Unit) : ((String) -> Unit) by debug {

    companion object {

        val Default: DefaultsDebugger get() = DefaultsDebugger { println(it) }
    }
}