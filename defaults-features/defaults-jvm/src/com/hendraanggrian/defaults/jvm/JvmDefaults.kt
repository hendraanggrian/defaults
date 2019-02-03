@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.defaults.jvm

import com.hendraanggrian.defaults.Defaults
import com.hendraanggrian.defaults.DefaultsSaver
import java.io.File
import java.util.prefs.Preferences

/** Creates defaults instance from file. */
inline operator fun Defaults.Companion.get(file: File): PropertiesFileDefaults =
    PropertiesFileDefaults(file)

/** Creates defaults instance from file. */
inline fun Defaults.Companion.bind(file: File, target: Any): DefaultsSaver =
    bind(get(file), target)

/** Creates defaults instance from preferences. */
inline operator fun Defaults.Companion.get(preferences: Preferences): PreferencesDefaults =
    PreferencesDefaults(preferences)

/** Creates defaults instance from file. */
inline fun Defaults.Companion.bind(preferences: Preferences, target: Any): DefaultsSaver =
    bind(get(preferences), target)