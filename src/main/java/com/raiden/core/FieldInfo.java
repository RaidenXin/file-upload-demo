package com.raiden.core;

import com.raiden.annotation.XMLNode;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Field 信息
 */
@Setter
@Getter
public class FieldInfo {

    private Field field;
    private Method getFieldValue;
    private Class<?> type;
    private XMLNode xmlNode;
    private boolean isDataConversion;
    private String functionName;

    public static FieldInfo builder(Field field,Method getFieldValue,Class<?> type,XMLNode xmlNode,boolean isDataConversion,String functionName){
        FieldInfo info = new FieldInfo();
        info.field = field;
        info.getFieldValue = getFieldValue;
        info.type = type;
        info.xmlNode = xmlNode;
        info.isDataConversion = isDataConversion;
        info.functionName = functionName;
        return info;
    }
}
