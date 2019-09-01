@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.prefs.jvm

import com.hendraanggrian.prefs.Prefs
import com.hendraanggrian.prefs.PrefsSaver
import com.hendraanggrian.prefs.internal.PrefsInternal
import java.io.File
import java.util.prefs.Preferences
import kotlin.reflect.KClass

/** Activates/deactivates debugging mode, which prints messages about preferences binding. */
fun Prefs.Companion.setDebug(debug: Boolean) =
    PrefsInternal.setDebugInternal(debug) { println(it) }

fun Prefs.Companion.of(source: File): PropertiesPrefs =
    PropertiesPrefs(source)

fun Prefs.Companion.safeOf(source: File): PropertiesPrefs =
    SafePropertiesPrefs(source)

fun Prefs.Companion.of(source: Preferences): Prefs2 =
    Prefs2(source)

fun Prefs.Companion.safeOf(source: Preferences): Prefs2 =
    SafePrefs2(source)

fun Prefs.Companion.userNode(type: KClass<*>): Prefs2 =
    Prefs2(Preferences.userNodeForPackage(type.java))

fun Prefs.Companion.safeUserNode(type: KClass<*>): Prefs2 =
    SafePrefs2(Preferences.userNodeForPackage(type.java))

inline fun <reified T> Prefs.Companion.userNode(): Prefs2 =
    userNode(T::class)

inline fun <reified T> Prefs.Companion.safeUserNode(): Prefs2 =
    safeUserNode(T::class)

fun Prefs.Companion.systemNode(type: KClass<*>): Prefs2 =
    Prefs2(Preferences.systemNodeForPackage(type.java))

fun Prefs.Companion.safeSystemNode(type: KClass<*>): Prefs2 =
    SafePrefs2(Preferences.systemNodeForPackage(type.java))

inline fun <reified T> Prefs.Companion.systemNode(): Prefs2 =
    systemNode(T::class)

inline fun <reified T> Prefs.Companion.safeSystemNode(): Prefs2 =
    safeSystemNode(T::class)

fun Prefs.Companion.userRoot(): Prefs2 =
    Prefs2(Preferences.userRoot())

fun Prefs.Companion.safeUserRoot(): Prefs2 =
    SafePrefs2(Preferences.userRoot())

fun Prefs.Companion.systemRoot(): Prefs2 =
    Prefs2(Preferences.systemRoot())

fun Prefs.Companion.safeSystemRoot(): Prefs2 =
    SafePrefs2(Preferences.systemRoot())

inline fun Prefs.Companion.bind(source: File, target: Any): PrefsSaver =
    of(source).bind(target)

inline fun Prefs.Companion.safeBind(source: File, target: Any): PrefsSaver =
    safeOf(source).bind(target)

inline fun Prefs.Companion.bind(source: Preferences, target: Any): PrefsSaver =
    of(source).bind(target)

inline fun Prefs.Companion.safeBind(source: Preferences, target: Any): PrefsSaver =
    safeOf(source).bind(target)
