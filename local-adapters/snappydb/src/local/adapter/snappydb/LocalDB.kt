package local.adapter.snappydb

import com.snappydb.DB
import local.EditableLocal

open class LocalDB internal constructor(private val nativeDB: DB) : EditableLocal {

    override fun contains(key: String): Boolean = nativeDB.exists(key)

    override fun get(key: String): String? = nativeDB.get(key)

    override fun getBoolean(key: String): Boolean? = nativeDB.getBoolean(key)

    override fun getDouble(key: String): Double? = nativeDB.getDouble(key)

    override fun getFloat(key: String): Float? = nativeDB.getFloat(key)

    override fun getLong(key: String): Long? = nativeDB.getLong(key)

    override fun getInt(key: String): Int? = nativeDB.getInt(key)

    override fun getShort(key: String): Short? = nativeDB.getShort(key)

    override fun getByte(key: String): Byte? = throw UnsupportedOperationException()

    override fun remove(key: String) = nativeDB.del(key)

    override fun clear() = nativeDB.destroy()

    override fun set(key: String, value: String) = nativeDB.put(key, value)

    override fun set(key: String, value: Boolean) = nativeDB.put(key, value)

    override fun set(key: String, value: Double) = nativeDB.put(key, value)

    override fun set(key: String, value: Float) = nativeDB.put(key, value)

    override fun set(key: String, value: Long) = nativeDB.put(key, value)

    override fun set(key: String, value: Int) = nativeDB.put(key, value)

    override fun set(key: String, value: Short) = nativeDB.put(key, value)

    override fun set(key: String, value: Byte): Unit = throw UnsupportedOperationException()

    override fun save() = nativeDB.close()

    override fun saveAsync() = nativeDB.close()
}
