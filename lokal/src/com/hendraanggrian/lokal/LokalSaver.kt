package com.hendraanggrian.lokal

import androidx.annotation.AnyThread
import androidx.annotation.WorkerThread

/** Base interface to save changes to local settings. */
interface LokalSaver {

    companion object {
        internal val EMPTY: LokalSaver = object : LokalSaver {
            override fun save() = saveAsync()
            override fun saveAsync() = Lokal.debug("WARNING: Saving an empty instance.")
        }
    }

    /** Non-blocking save in the background. */
    @WorkerThread
    fun save()

    /** Blocking save. */
    @AnyThread
    fun saveAsync()
}