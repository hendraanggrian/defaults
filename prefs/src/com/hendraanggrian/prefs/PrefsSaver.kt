package com.hendraanggrian.prefs

import androidx.annotation.AnyThread
import androidx.annotation.WorkerThread
import com.hendraanggrian.prefs.internal.PrefsInternal

/** Base interface to save changes to local settings. */
interface PrefsSaver {

    companion object {
        internal val EMPTY: PrefsSaver = object : PrefsSaver {
            override fun save() = saveAsync()
            override fun saveAsync() = PrefsInternal.debug("WARNING: Saving an empty instance.")
        }
    }

    /** Non-blocking save in the background. */
    @WorkerThread fun save()

    /** Blocking save. */
    @AnyThread fun saveAsync()
}
