package com.hendraanggrian.local.jvm

import java.io.File
import java.util.Properties

class SimpleLocalProperties : LocalProperties {

    constructor(nativeProperties: Properties, targetFile: File) :
        super(nativeProperties, targetFile)

    constructor(targetFile: File) : super(targetFile)

    override fun getBoolean(key: String): Boolean? = get(key)?.toBoolean()

    override fun getDouble(key: String): Double? = get(key)?.toDouble()

    override fun getFloat(key: String): Float? = get(key)?.toFloat()

    override fun getLong(key: String): Long? = get(key)?.toLong()

    override fun getInt(key: String): Int? = get(key)?.toInt()

    override fun getShort(key: String): Short? = get(key)?.toShort()

    override fun getByte(key: String): Byte? = get(key)?.toByte()

    override fun set(key: String, value: Boolean) = set(key, value.toString())

    override fun set(key: String, value: Double) = set(key, value.toString())

    override fun set(key: String, value: Float) = set(key, value.toString())

    override fun set(key: String, value: Long) = set(key, value.toString())

    override fun set(key: String, value: Int) = set(key, value.toString())

    override fun set(key: String, value: Short) = set(key, value.toString())

    override fun set(key: String, value: Byte) = set(key, value.toString())
}
