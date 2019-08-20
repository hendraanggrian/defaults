package local

/** Represents a runnable with string param. */
open class LocalLogger(log: (String) -> Unit) : (String) -> Unit by log {

    /** Features often use this companion as receiver. */
    companion object {

        /** Default debugger, which just prints in system. */
        val System: LocalLogger get() = LocalLogger { println(it) }
    }
}
