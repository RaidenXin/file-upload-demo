package com.raiden.annotation;

import com.raiden.util.LocalDateUtils;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface XMLNode {
    /**
     * 节点名称
     * @return
     */
    String nameOfTheNod();
    /**
     * 序列化转
     * @return
     */
    XMLSerialization serialization() default @XMLSerialization;
    /**
     * 反序列化
     */
    XMLDeserialization deserialization() default @XMLDeserialization;
    /**
     * 字段属性
     * @return
     */
    XMLAttribute[] attributes() default {};
}
