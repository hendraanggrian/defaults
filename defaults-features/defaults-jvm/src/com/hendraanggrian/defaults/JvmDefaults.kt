@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.defaults

import java.io.File
import java.util.prefs.Preferences

/** Creates defaults instance from file. */
inline operator fun Defaults.Companion.get(file: File): PropertiesFileDefaults =
    PropertiesFileDefaults(file)

/** Creates defaults instance from preferences. */
inline operator fun Defaults.Companion.get(preferences: Preferences): PreferencesDefaults =
    PreferencesDefaults(preferences)