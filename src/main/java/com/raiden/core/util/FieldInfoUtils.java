package com.raiden.core.util;

import com.huazhu.deploycontrol.core.annotation.XMLNode;
import com.huazhu.deploycontrol.core.info.FieldInfo;
import com.huazhu.deploycontrol.core.info.XMLBeanInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class FieldInfoUtils {

    /**
     * 这里设置了一个缓存
     */
    private static final Map<Class<?>, XMLBeanInfo> cache = new ConcurrentHashMap<>();

    private static final String GET = "get";
    private static final String SET = "set";

    public static XMLBeanInfo builder(Class<?> clazz){
        if (clazz == null){
            return new XMLBeanInfo();
        }
        //从缓存中取出
        XMLBeanInfo beanInfo = cache.get(clazz);
        //如果缓存中存在 就直接返回
        if (beanInfo != null){
            return beanInfo;
        }
        Field[] fields = clazz.getDeclaredFields();
        FieldInfo[] result = new FieldInfo[fields.length];
        int index = 0;
        for (Field field : fields){
            field.setAccessible(true);
            XMLNode annotation = field.getAnnotation(XMLNode.class);
            Class<?> declaringClass = field.getDeclaringClass();
            try {
                Method getFieldValue = declaringClass.getDeclaredMethod(GET + firstLetterCapitalized(field.getName()));
                Method setFieldValue = declaringClass.getDeclaredMethod(SET + firstLetterCapitalized(field.getName()), field.getType());
                result[index++] = FieldInfo.builder(field, getFieldValue, setFieldValue, annotation, annotation.serialization(), annotation.deserialization());
            } catch (NoSuchMethodException e) {
                continue;
            }
        }
        beanInfo = new XMLBeanInfo(result);
        //放入缓存中
        cache.put(clazz, beanInfo);
        return beanInfo;
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
