package com.hendraanggrian.prefs

/**
 * Subinterface of [Prefs] that, in contrast to [EditablePrefs],
 * can directly modify preferences since it also inherits [Prefs.Editor].
 */
interface SimplePrefs : Prefs, Prefs.Editor
