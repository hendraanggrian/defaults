package com.hendraanggrian.prefs

/**
 * Subinterface of [ReadablePrefs] that, in contrast to [EditablePrefs],
 * can directly modify preferences since it also inherits [Prefs.Editor].
 */
interface WritablePrefs : ReadablePrefs, Prefs.Editor
