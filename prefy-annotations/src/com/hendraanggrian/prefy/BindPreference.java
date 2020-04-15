package com.hendraanggrian.prefy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Bind a preference value to JVM field with this annotation.
 * Keep in mind that in order to use this feature, `prefy-compiler` must be imported as annotation processor.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface BindPreference {

    /**
     * Key of the preference which value will be injected into the annotated field.
     * If no key is supplied (default behavior), field name will be used as the key.
     *
     * @return preference key/name.
     */
    String value() default "";
}