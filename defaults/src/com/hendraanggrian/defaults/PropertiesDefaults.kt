package com.hendraanggrian.defaults

import com.hendraanggrian.defaults.internal.BooleanDefaultsEditorSupport
import com.hendraanggrian.defaults.internal.BooleanReadableDefaultsSupport
import com.hendraanggrian.defaults.internal.ByteDefaultsEditorSupport
import com.hendraanggrian.defaults.internal.ByteReadableDefaultsSupport
import com.hendraanggrian.defaults.internal.DoubleDefaultsEditorSupport
import com.hendraanggrian.defaults.internal.DoubleReadableDefaultsSupport
import com.hendraanggrian.defaults.internal.FloatDefaultsEditorSupport
import com.hendraanggrian.defaults.internal.FloatReadableDefaultsSupport
import com.hendraanggrian.defaults.internal.IntDefaultsEditorSupport
import com.hendraanggrian.defaults.internal.IntReadableDefaultsSupport
import com.hendraanggrian.defaults.internal.LongDefaultsEditorSupport
import com.hendraanggrian.defaults.internal.LongReadableDefaultsSupport
import com.hendraanggrian.defaults.internal.ShortDefaultsEditorSupport
import com.hendraanggrian.defaults.internal.ShortReadableDefaultsSupport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.util.Properties

class PropertiesDefaults(val file: File) : WritableDefaults,
    BooleanReadableDefaultsSupport, BooleanDefaultsEditorSupport,
    DoubleReadableDefaultsSupport, DoubleDefaultsEditorSupport,
    FloatReadableDefaultsSupport, FloatDefaultsEditorSupport,
    LongReadableDefaultsSupport, LongDefaultsEditorSupport,
    IntReadableDefaultsSupport, IntDefaultsEditorSupport,
    ShortReadableDefaultsSupport, ShortDefaultsEditorSupport,
    ByteReadableDefaultsSupport, ByteDefaultsEditorSupport {

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