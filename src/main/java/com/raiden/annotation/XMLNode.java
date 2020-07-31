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
     * 是否进行数据转换
     * @return
     */
    boolean isDataConversion() default false;
    /**
     * 数据格式转换方法
     */
    String dateFormatFunction() default "";
    /**
     * 字段属性
     * @return
     */
    XMLAttribute[] attributes() default {};
}
