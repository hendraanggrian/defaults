package com.hendraanggrian.lokal

import com.hendraanggrian.lokal.internal.BooleanLokalEditorSupport
import com.hendraanggrian.lokal.internal.BooleanReadableLokalSupport
import com.hendraanggrian.lokal.internal.ByteLokalEditorSupport
import com.hendraanggrian.lokal.internal.ByteReadableLokalSupport
import com.hendraanggrian.lokal.internal.DoubleLokalEditorSupport
import com.hendraanggrian.lokal.internal.DoubleReadableLokalSupport
import com.hendraanggrian.lokal.internal.FloatLokalEditorSupport
import com.hendraanggrian.lokal.internal.FloatReadableLokalSupport
import com.hendraanggrian.lokal.internal.IntLokalEditorSupport
import com.hendraanggrian.lokal.internal.IntReadableLokalSupport
import com.hendraanggrian.lokal.internal.LongLokalEditorSupport
import com.hendraanggrian.lokal.internal.LongReadableLokalSupport
import com.hendraanggrian.lokal.internal.ShortLokalEditorSupport
import com.hendraanggrian.lokal.internal.ShortReadableLokalSupport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.util.Properties

class PropertiesLokal(val file: File) : WritableLokal,
    BooleanReadableLokalSupport, BooleanLokalEditorSupport,
    DoubleReadableLokalSupport, DoubleLokalEditorSupport,
    FloatReadableLokalSupport, FloatLokalEditorSupport,
    LongReadableLokalSupport, LongLokalEditorSupport,
    IntReadableLokalSupport, IntLokalEditorSupport,
    ShortReadableLokalSupport, ShortLokalEditorSupport,
    ByteReadableLokalSupport, ByteLokalEditorSupport {

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

    override fun minusAssign(key: String) {
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