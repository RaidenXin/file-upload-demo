package com.raiden.annotation;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 16:42 2020/8/2
 * @Modified By:
 */
public @interface XMLDeserialization {
    /**
     * 是否进行数据转换
     * @return
     */
    boolean isDataConversion() default false;
    /**
     * 数据格式转换方法
     */
    String[] dateFormatFunctions() default {};
}
