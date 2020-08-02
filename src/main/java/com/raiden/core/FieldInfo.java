package com.raiden.core;

import com.raiden.annotation.XMLDeserialization;
import com.raiden.annotation.XMLNode;
import com.raiden.annotation.XMLSerialization;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Field 信息
 */
@Setter
@Getter
public final class FieldInfo {

    private Field field;
    private Method getFieldValue;
    private Method setFieldValue;
    private Class<?> type;
    private XMLNode xmlNode;
    private boolean isDataConversionOfSerialization;
    private String[] functionNamesOfSerialization;
    private boolean isDataConversionOfDeserialization;
    private String[] functionNamesOfDeserialization;
    private Class<?> genericityType;

    public static FieldInfo builder(Field field, Method getFieldValue, Method setFieldValue, XMLNode xmlNode, XMLSerialization serialization,XMLDeserialization deserialization){
        FieldInfo info = new FieldInfo();
        info.field = field;
        info.getFieldValue = getFieldValue;
        info.setFieldValue = setFieldValue;
        info.type = field.getType();
        info.xmlNode = xmlNode;
        info.isDataConversionOfSerialization = serialization.isDataConversion();
        info.functionNamesOfSerialization = serialization.dateFormatFunctions();
        info.isDataConversionOfDeserialization = deserialization.isDataConversion();
        info.functionNamesOfDeserialization = deserialization.dateFormatFunctions();
        if (Collection.class.isAssignableFrom(info.type)){
            ParameterizedType genericType = (ParameterizedType) field.getGenericType();
            Type[] actualTypeArguments = genericType.getActualTypeArguments();
            if (actualTypeArguments != null && actualTypeArguments.length > 0){
                info.genericityType = (Class<?>) actualTypeArguments[0];
            }
        }
        return info;
    }
}
