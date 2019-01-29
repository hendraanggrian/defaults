package com.hendraanggrian.tools.defaults.internal

import com.google.auto.common.MoreElements
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName
import javax.lang.model.element.Element

/** Represents SharedPreferences-compatible field types. */
internal enum class PreferenceType constructor(private val mTypeName: TypeName) {
    STRING(TypeName.get(String::class.java)),
    SET(ParameterizedTypeName.get(Set::class.java, String::class.java)),
    INT(TypeName.INT),
    LONG(TypeName.LONG),
    FLOAT(TypeName.FLOAT),
    BOOLEAN(TypeName.BOOLEAN);

    companion object {
        /**
         * Guess the correct SharedPreference type of a field Element.
         *
         * @param fieldElement annotated with Preferencer annotations.
         * @return type of this field Element, or null if this Element is Preference.
         */
        fun valueOf(fieldElement: Element): PreferenceType? {
            val typeName = TypeName.get(fieldElement.asType())
            if (typeName.toString().startsWith("android.preference") ||
                typeName.toString().startsWith("androidx.preference")
            ) {
                return null
            }
            values()
                .filter { typeName == it.mTypeName }
                .forEach { return it }
            throw IllegalArgumentException(
                MoreElements.asType(fieldElement.enclosingElement).simpleName.toString() +
                    "#" +
                    fieldElement.simpleName +
                    " has unsupported type " +
                    typeName
            )
        }
    }
}