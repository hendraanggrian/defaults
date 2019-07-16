package com.hendraanggrian.local

import com.hendraanggrian.local.internal.BooleanLocalEditorSupport
import com.hendraanggrian.local.internal.BooleanLocalSupport
import com.hendraanggrian.local.internal.ByteLocalEditorSupport
import com.hendraanggrian.local.internal.ByteLocalSupport
import com.hendraanggrian.local.internal.DoubleLocalEditorSupport
import com.hendraanggrian.local.internal.DoubleLocalSupport
import com.hendraanggrian.local.internal.FloatLocalEditorSupport
import com.hendraanggrian.local.internal.FloatLocalSupport
import com.hendraanggrian.local.internal.IntLocalEditorSupport
import com.hendraanggrian.local.internal.IntLocalSupport
import com.hendraanggrian.local.internal.LongLocalEditorSupport
import com.hendraanggrian.local.internal.LongLocalSupport
import com.hendraanggrian.local.internal.ShortLocalEditorSupport
import com.hendraanggrian.local.internal.ShortLocalSupport
import java.io.File
import java.util.Properties
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LocalProperties(private val file: File) : Local, Local.Editor,
    BooleanLocalSupport, BooleanLocalEditorSupport,
    DoubleLocalSupport, DoubleLocalEditorSupport,
    FloatLocalSupport, FloatLocalEditorSupport,
    LongLocalSupport, LongLocalEditorSupport,
    IntLocalSupport, IntLocalEditorSupport,
    ShortLocalSupport, ShortLocalEditorSupport,
    ByteLocalSupport, ByteLocalEditorSupport {

    private val properties = Properties()

    init {
        if (!file.exists()) {
            file.createNewFile()
        }
        file.inputStream().use { properties.load(it) }
    }

    override fun contains(key: String): Boolean = properties.containsKey(key)

    override fun get(key: String): String? = properties.getProperty(key)

    override fun getOrDefault(key: String, defaultValue: String): String =
        properties.getProperty(key, defaultValue)

    override val editor: Local.Editor get() = this

    override fun remove(key: String) {
        properties.remove(key)
    }

    override fun clear() = properties.clear()

    override fun set(key: String, value: String?) {
        properties.setProperty(key, value)
    }

    override fun save() {
        GlobalScope.launch(Dispatchers.IO) {
            saveAsync()
        }
    }

    override fun saveAsync() {
        file.outputStream().use {
            properties.store(it, null)
        }
    }
}
