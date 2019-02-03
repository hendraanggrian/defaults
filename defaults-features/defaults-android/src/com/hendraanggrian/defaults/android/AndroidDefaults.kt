@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.defaults.android

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.hendraanggrian.defaults.Defaults
import com.hendraanggrian.defaults.DefaultsSaver

/** Creates defaults instance from shared preferences. */
inline operator fun Defaults.Companion.get(pref: SharedPreferences): SharedPreferencesDefaults =
    SharedPreferencesDefaults(pref)

/** Creates defaults instance from shared preferences. */
inline fun Defaults.Companion.bind(
    pref: SharedPreferences,
    target: Any
): DefaultsSaver =
    bind(get(pref), target)

/** Creates defaults instance from default shared preferences in context. */
inline operator fun Defaults.Companion.get(context: Context): SharedPreferencesDefaults =
    get(PreferenceManager.getDefaultSharedPreferences(context))

/** Creates defaults instance from default shared preferences in context. */
inline fun Defaults.Companion.bind(context: Context, target: Any): DefaultsSaver =
    bind(PreferenceManager.getDefaultSharedPreferences(context), target)

/** Creates defaults instance from default shared preferences in context. */
inline fun Defaults.Companion.bind(context: Context): DefaultsSaver =
    bind(PreferenceManager.getDefaultSharedPreferences(context), context)