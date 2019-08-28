package local.internal

object LocalInternal {
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
