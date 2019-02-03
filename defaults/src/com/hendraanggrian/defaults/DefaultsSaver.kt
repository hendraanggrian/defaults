package com.hendraanggrian.defaults

import androidx.annotation.AnyThread
import androidx.annotation.WorkerThread

/** Base interface to save changes to local settings. */
interface DefaultsSaver {

    companion object {
        internal val EMPTY: DefaultsSaver = object : DefaultsSaver {
            override fun save() = saveAsync()
            override fun saveAsync() = Defaults.debug("WARNING: Saving an empty instance.")
        }
    }

    /** Non-blocking save in the background. */
    @WorkerThread
    fun save()

    /** Blocking save. */
    @AnyThread
    fun saveAsync()
}