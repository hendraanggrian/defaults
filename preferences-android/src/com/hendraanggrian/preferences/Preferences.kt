@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.preferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

fun Preferences.Companion.android(preferences: SharedPreferences): Preferences<*> =
    AndroidPreferences(preferences)

inline fun Preferences.Companion.android(context: Context): Preferences<*> =
    android(PreferenceManager.getDefaultSharedPreferences(context))