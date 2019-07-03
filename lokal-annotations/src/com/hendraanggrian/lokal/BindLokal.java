package com.hendraanggrian.lokal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Bind SharedPreferences value to field with this annotation.
 * Key of SharedPreferences entry of which value will be injected to annotated field.
 * If no key is supplied (default behavior), field name will be used as the key.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface BindLokal {

    String SUFFIX = "LokalBinding";

    String value() default "";
}