package com.hendraanggrian.lokal

import com.hendraanggrian.lokal.internal.BooleanLokalEditorSupport
import com.hendraanggrian.lokal.internal.BooleanLokalSupport
import com.hendraanggrian.lokal.internal.ByteLokalEditorSupport
import com.hendraanggrian.lokal.internal.ByteLokalSupport
import com.hendraanggrian.lokal.internal.DoubleLokalEditorSupport
import com.hendraanggrian.lokal.internal.DoubleLokalSupport
import com.hendraanggrian.lokal.internal.FloatLokalEditorSupport
import com.hendraanggrian.lokal.internal.FloatLokalSupport
import com.hendraanggrian.lokal.internal.IntLokalEditorSupport
import com.hendraanggrian.lokal.internal.IntLokalSupport
import com.hendraanggrian.lokal.internal.LongLokalEditorSupport
import com.hendraanggrian.lokal.internal.LongLokalSupport
import com.hendraanggrian.lokal.internal.ShortLokalEditorSupport
import com.hendraanggrian.lokal.internal.ShortLokalSupport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.util.Properties

class LokalProperties(private val file: File) : Lokal, Lokal.Editor,
    BooleanLokalSupport, BooleanLokalEditorSupport,
    DoubleLokalSupport, DoubleLokalEditorSupport,
    FloatLokalSupport, FloatLokalEditorSupport,
    LongLokalSupport, LongLokalEditorSupport,
    IntLokalSupport, IntLokalEditorSupport,
    ShortLokalSupport, ShortLokalEditorSupport,
    ByteLokalSupport, ByteLokalEditorSupport {

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

    override val editor: Lokal.Editor get() = this

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