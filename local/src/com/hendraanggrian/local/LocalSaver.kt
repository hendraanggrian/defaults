package com.hendraanggrian.local

import androidx.annotation.AnyThread
import androidx.annotation.WorkerThread

/** Base interface to save changes to local settings. */
interface LocalSaver {

    companion object {
        internal val EMPTY: LocalSaver = object : LocalSaver {
            override fun save() = saveAsync()
            override fun saveAsync() = ReadableLocal.debug("WARNING: Saving an empty instance.")
        }
    }

    /** Non-blocking save in the background. */
    @WorkerThread
    fun save()

    /** Blocking save. */
    @AnyThread
    fun saveAsync()
}
