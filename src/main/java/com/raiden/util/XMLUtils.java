package com.raiden.util;

import com.raiden.annotation.XMLAttribute;
import com.raiden.annotation.XMLNode;
import com.raiden.core.FieldInfo;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;


/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 23:38 2020/7/30
 * @Modified By:
 */
public final class XMLUtils {

    private static final String MESSAGE = "MESSAGE";

    private static final String NAME = "name";

    private static final String PATH = "user.xml";

    /**
     * 将Java对象序列化成XML格式
     * @param bean
     * @return
     */
    public static File serializeToXML(Object bean) {
        if (bean == null) {
            return null;
        }
        File file = new File(PATH);
        Document doc = DocumentHelper.createDocument();
        //添加根节点
        Element message = doc.addElement(MESSAGE);
        serialize(message, bean);
        try {
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
            writer.write(doc);
            return file;
        }catch (IOException e){
            return null;
        }
    }

    private static void serialize(Element message,Object bean){
        if (bean == null){
            return;
        }
        Class<?> clazz = bean.getClass();
        XMLNode annotation = clazz.getAnnotation(XMLNode.class);
        Element element = message.addElement(annotation.nameOfTheNod());
        XMLAttribute[] xmlAttributes = annotation.attributes();
        for(XMLAttribute xmlAttribute : xmlAttributes){
            element.addAttribute(xmlAttribute.key(), xmlAttribute.value());
        }
        Field[] declaredFields = clazz.getDeclaredFields();
        FieldInfo[] fieldInfos = FieldInfoUtils.builder(declaredFields);
        for (FieldInfo info : fieldInfos){
            if (info == null){
                continue;
            }
            Class<?> type = info.getType();
            XMLNode xmlNode = info.getXmlNode();
            XMLAttribute[] attributes = xmlNode.attributes();
            XMLNode node = type.getAnnotation(XMLNode.class);
            //判断是不是基础数据类型
            if (node != null){
                Method getFieldValue = info.getGetFieldValue();
                try {
                    Object invoke = getFieldValue.invoke(bean);
                    serialize(element, invoke);
                } catch (Exception e) {
                    continue;
                }
            }if (Collection.class.isAssignableFrom(type)){
                Method getFieldValue = info.getGetFieldValue();
                //如果他是List 进行特殊操作
                try {
                    Element item = element.addElement(xmlNode.nameOfTheNod());
                    Collection<?> list =  (Collection<?>) getFieldValue.invoke(bean);
                    for (Object o : list){
                        if (o == null){
                            continue;
                        }
                        Class<?> aClass = o.getClass();
                        XMLNode xmlNode1 = aClass.getAnnotation(XMLNode.class);
                        if (xmlNode1 != null){
                            serialize(item, o);
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
            }if (type.isArray()){
                Method getFieldValue = info.getGetFieldValue();
                //如果他是List 进行特殊操作
                try {
                    Element item = element.addElement(xmlNode.nameOfTheNod());
                    Object[] array = (Object[]) getFieldValue.invoke(bean);
                    for (Object o : array){
                        if (o != null){
                            Class<?> aClass = o.getClass();
                            XMLNode xmlNode1 = aClass.getAnnotation(XMLNode.class);
                            if (xmlNode1 != null){
                                serialize(item, o);
                            }
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
            }else {
                Element item = element.addElement(xmlNode.nameOfTheNod());
                for(XMLAttribute xmlAttribute : attributes){
                    String value = xmlAttribute.value();
                    Method getFieldValue = info.getGetFieldValue();
                    try {
                        boolean isBlank = StringUtils.isBlank(value);
                        String attributeValue = isBlank? String.valueOf(getFieldValue.invoke(bean)) : value;
                        String functionName;
                        //如果是时间类 且 格式化方法名称不为空 进行格式化
                        if (isBlank && info.isDataConversion() && StringUtils.isNotBlank(functionName = info.getFunctionName())){
                            item.addAttribute(xmlAttribute.key(), LocalDateUtils.executeFunction(functionName, attributeValue));
                        }else {
                            item.addAttribute(xmlAttribute.key(), attributeValue);
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
        }
    }
}
