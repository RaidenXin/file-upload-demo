package com.raiden.util;

import com.raiden.annotation.XMLNode;
import com.raiden.core.FieldInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class FieldInfoUtils {

    private static final String GET = "get";

    public static FieldInfo[] builder(Field... fields){
        if (fields == null){
            return new FieldInfo[0];
        }
        FieldInfo[] result = new FieldInfo[fields.length];
        int index = 0;
        for (Field field : fields){
            field.setAccessible(true);
            XMLNode annotation = field.getAnnotation(XMLNode.class);
            Class<?> declaringClass = field.getDeclaringClass();
            try {
                Method method = declaringClass.getDeclaredMethod(GET + firstLetterCapitalized(field.getName()));
                result[index++] = FieldInfo.builder(field, method, field.getType(), annotation, annotation.isDataConversion(), annotation.dateFormatFunction());
            } catch (NoSuchMethodException e) {
                continue;
            }
        }
        return result;
    }

    /**
     * 首字母大写的方法
     * @param name
     * @return
     */
    public static String firstLetterCapitalized(String name){
        char[] chars = name.toCharArray();
        StringBuilder builder = new StringBuilder();
        char c = chars[0];
        //如果是小写才替换
        if (c > 96 && c < 123){
            c -= 32;
            chars[0] = c;

        }
        builder.append(chars);
        return builder.toString();
    }
}