package com.hendraanggrian.local.jvm

import com.hendraanggrian.local.Local
import com.hendraanggrian.local.LocalSaver
import java.io.File
import java.util.prefs.Preferences

fun Local.bind(source: File, target: Any): LocalSaver =
    bind(LocalProperties(source), target)

fun Local.bind(source: Preferences, target: Any): LocalSaver =
    bind(LocalPreferences(source), target)