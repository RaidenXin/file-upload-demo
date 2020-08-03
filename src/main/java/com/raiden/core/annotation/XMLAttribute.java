package com.raiden.core.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface XMLAttribute {
    String key();
    String value() default "";
}
