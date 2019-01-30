@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.defaults

import java.io.File
import java.util.prefs.Preferences

/** Creates defaults instance from file. */
inline fun Defaults.Companion.from(file: File): PropertiesFileDefaults =
    PropertiesFileDefaults(file)

/** Creates defaults instance from preferences. */
inline fun Defaults.Companion.from(preferences: Preferences): PreferencesDefaults =
    PreferencesDefaults(preferences)