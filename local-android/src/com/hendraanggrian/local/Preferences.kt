@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.local

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

infix fun Local.Companion.sharedPreferences(preferences: SharedPreferences): Local<*> =
    AndroidLocal(preferences)

inline infix fun Local.Companion.context(context: Context): Local<*> =
    sharedPreferences(PreferenceManager.getDefaultSharedPreferences(context))