package com.hendraanggrian.local;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Bind SharedPreferences value to field with this annotation.
 * Key of SharedPreferences entry of which value will be injected to annotated field.
 * If no key is supplied (default behavior), field name will be used as the key.
 */
@Retention(CLASS)
@Target(FIELD)
public @interface BindLocal {

    String SUFFIX = "_LocalBinding";

    String value() default "";
}