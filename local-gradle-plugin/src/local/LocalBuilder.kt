package local

import java.util.function.Function
import kotlin.reflect.KClass

open class LocalBuilder {

    fun <T> add(key: String, type: Class<T>) {
    }

    fun <T : Any> add(key: String, type: KClass<T>) =
        add(key, type.java)

    inline fun <reified T : Any> String.invoke() =
        add(this, T::class)

    fun <T, R> add(
        key: String,
        type: Class<T>,
        actualType: Class<R>,
        callable: Function<T, R>
    ) {
    }

    fun <T : Any, R : Any> add(
        key: String,
        type: KClass<T>,
        actualType: KClass<R>,
        callable: Function<T, R>
    ) = add(key, type.java, actualType.java, callable)

    inline fun <reified T : Any, reified R : Any> String.invoke(noinline callable: (T) -> R) =
        add(this, T::class, R::class, callable)
}
