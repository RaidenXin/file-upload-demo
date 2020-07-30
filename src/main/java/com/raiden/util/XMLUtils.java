package com.raiden.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.commons.lang3.StringUtils;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 23:38 2020/7/30
 * @Modified By:
 */
public final class XMLUtils {
    /**
     * 将Java对象序列化成XML格式
     * @param o
     * @return
     */
    public static String serializeToXML(Object o){
        if (o == null){
            return StringUtils.EMPTY;
        }
        // 将employee对象序列化为XML
        XStream xStream = new XStream(new DomDriver());
        // 设置employee类的别名
        Class<?> aClass = o.getClass();
        xStream.alias(aClass.getSimpleName(), aClass);
        String personXml = xStream.toXML(o);
        return personXml;
    }
}
