package com.raiden.util;

import com.raiden.annotation.XMLAttribute;
import com.raiden.annotation.XMLNode;
import com.raiden.content.DataFormatStrategyContext;
import com.raiden.core.FieldInfo;
import com.raiden.core.XMLBeanInfo;
import com.raiden.core.model.VerifyFingerprintResp;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 23:38 2020/7/30
 * @Modified By:
 */
public final class XMLUtils {

    /**
     * 将Java对象序列化成XML格式
     * @param bean
     * @return
     */
    public static File toXMLFile(Object bean,String path) {
        if (bean == null) {
            return null;
        }
        File file = new File(path);
        Document doc = DocumentHelper.createDocument();
        serialize(doc, bean);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)){
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter(fileOutputStream, format);
            writer.write(doc);
            return file;
        }catch (IOException e){
            return null;
        }
    }

    public static String serialize(Object bean){
        Document doc = DocumentHelper.createDocument();
        serialize(doc, bean);
        return doc.asXML();
    }

    private static void serialize(Branch branch,Object bean){
        Class<?> clazz = bean.getClass();
        XMLNode rootNode = clazz.getAnnotation(XMLNode.class);
        if (rootNode == null){
            return;
        }
        Element element = branch.addElement(rootNode.nameOfTheNod());
        XMLAttribute[] xmlAttributes = rootNode.attributes();
        for(XMLAttribute xmlAttribute : xmlAttributes){
            element.addAttribute(xmlAttribute.key(), xmlAttribute.value());
        }
        XMLBeanInfo beanInfo = FieldInfoUtils.builder(clazz);
        FieldInfo[] fieldInfos = beanInfo.getFieldInfos();
        for (FieldInfo info : fieldInfos){
            if (info == null){
                continue;
            }
            Class<?> type = info.getType();
            XMLNode xmlNode = info.getXmlNode();
            XMLAttribute[] attributes = xmlNode.attributes();
            //判断是不是基础数据类型
            if (type.getAnnotation(XMLNode.class) != null){
                Method getFieldValue = info.getGetFieldValue();
                try {
                    Object invoke = getFieldValue.invoke(bean);
                    serialize(element, invoke);
                } catch (Exception e) {
                    continue;
                }
            }else if (Collection.class.isAssignableFrom(type)){
                Method getFieldValue = info.getGetFieldValue();
                //如果他是List 进行特殊操作
                try {
                    Element item = element.addElement(xmlNode.nameOfTheNod());
                    Collection<?> list =  (Collection<?>) getFieldValue.invoke(bean);
                    for (Object o : list){
                        if (o == null){
                            continue;
                        }
                        serialize(item, o);
                    }
                } catch (Exception e) {
                    continue;
                }
            }else if (type.isArray()){
                Method getFieldValue = info.getGetFieldValue();
                //如果他是List 进行特殊操作
                try {
                    Element item = element.addElement(xmlNode.nameOfTheNod());
                    Object[] array = (Object[]) getFieldValue.invoke(bean);
                    for (Object o : array){
                        if (o == null){
                            continue;
                        }
                        serialize(item, o);
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
                        String[] functionNames;
                        //需要进行格式化 且 格式化方法名称不为空 进行格式化
                        if (isBlank && info.isDataConversionOfSerialization() && (functionNames = info.getFunctionNamesOfSerialization()).length > 0){
                            item.addAttribute(xmlAttribute.key(), DataFormatStrategyContext.executeFunctions(functionNames, attributeValue));
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

    public static <T> T deserialize(String xml,Class<T> type){
        if (StringUtils.isBlank(xml) || type == null){
            return null;
        }
        Document document;
        try {
            document = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            return null;
        }
        Element rootElement = document.getRootElement();
        return deserialize(rootElement, type);
    }

    private static <T> T deserialize(Element element,Class<T> type){
        XMLNode xmlNode = type.getAnnotation(XMLNode.class);
        String nodeName;
        if (xmlNode == null || StringUtils.isBlank(nodeName = xmlNode.nameOfTheNod())){
            return null;
        }
        //如果指纹相同 才判断 是 映射关系正确
        if (verifyFingerprint(xmlNode, element).isSuccess()){
            Object bean;
            try {
                bean = type.newInstance();
            } catch (Exception e) {
                //如果报错 直接返回
                e.printStackTrace();
                return null;
            }
            XMLBeanInfo beanInfo = FieldInfoUtils.builder(type);
            FieldInfo[] fieldInfos = beanInfo.getFieldInfos();
            //要删除 的节点 每解析完成一个节点 删除一个节点
            for (FieldInfo info : fieldInfos){
                Element deleteElement = null;
                XMLNode node = info.getXmlNode();
                if (node == null){
                    continue;
                }
                Class<?> infoType = info.getType();
                List<Element> elements = element.elements(node.nameOfTheNod());
                XMLNode annotation = infoType.getAnnotation(XMLNode.class);
                Object args = null;
                if (annotation != null){
                    //如果是一个带注解的实例类
                    for (Element e : elements){
                        if ((args = deserialize(e, infoType)) != null){
                            deleteElement = e;
                            break;
                        }
                    }
                }else if (Collection.class.isAssignableFrom(infoType)){
                    //如果是一个 List
                    List values = new ArrayList<>();
                    Class<?> c = info.getGenericityType();
                    if (c == null){
                        continue;
                    }
                    boolean isSuccess = false;
                    for (Element e : elements){
                        List<Element> elementList = e.elements();
                        for (Element el : elementList){
                            Object value;
                            if ((value = deserialize(el, c)) == null){
                                break;
                            }
                            values.add(value);
                            isSuccess = true;
                        }
                        if (isSuccess){
                            deleteElement = e;
                            break;
                        }
                    }
                    args = values;
                }else if (infoType.isArray()){
                    //如果是一个数组
                    //获取 数组的类型
                    Class<?> componentType = type.getComponentType();
                    List values = new ArrayList<>();
                    if (componentType == null){
                        continue;
                    }
                    for (Element e : elements){
                        List<Element> elementList = e.elements();
                        for (Element el : elementList){
                            Object value;
                            if ((value = deserialize(el, componentType)) == null){
                                break;
                            }
                            values.add(value);
                        }
                    }
                    args = values.toArray();
                }else {
                    //其余的是 基础属性 如 String int 等
                    for (Element e : elements){
                        VerifyFingerprintResp resp = verifyFingerprint(node, e);
                        if (resp.isSuccess()){
                            String value = info.isDataConversionOfDeserialization() ?
                                    DataFormatStrategyContext.executeFunctions(info.getFunctionNamesOfDeserialization(), resp.getValue()) : resp.getValue();
                            deleteElement = e;
                            if (infoType == Long.class){
                                args = Long.valueOf(value);
                            }else if (infoType == long.class){
                                args = Long.parseLong(value);
                            }else if (infoType == Float.class){
                                args = Float.valueOf(value);
                            }else if (infoType == float.class){
                                args = Float.parseFloat(value);
                            }else if (infoType == Integer.class){
                                args = Integer.valueOf(value);
                            }else if (infoType == int.class){
                                args = Integer.parseInt(value);
                            }else if (infoType == Boolean.class){
                                args = Boolean.valueOf(value);
                            }else if (infoType == boolean.class){
                                args = Boolean.parseBoolean(value);
                            }else {
                                args = value;
                            }
                        }
                    }
                }
                Method setFieldValue = info.getSetFieldValue();
                try {
                    setFieldValue.invoke(bean, args);
                } catch (Exception e) {
                }
                if (deleteElement != null){
                    //如果解析完成就删除 该节点
                    element.remove(deleteElement);
                }
            }
            return (T) bean;
        }
        return null;
    }

    /**
     * 验证 类或者属性 和 节点 的指纹是否匹配
     */
    private static VerifyFingerprintResp verifyFingerprint(XMLNode xmlNode,Element element){
        if (xmlNode == null || element == null){
            return VerifyFingerprintResp.FAIL;
        }
        //这里是bean 的签名(即为类或者属性上 注解中节点名称 和 属性key和value的全部信息)
        StringBuilder beanSign = new StringBuilder(xmlNode.nameOfTheNod());
        //这里是 节点的签名(即为 节点名称 和 属性key和value的全部信息)
        StringBuilder elementSign = new StringBuilder(element.getName());
        String temp = null;
        XMLAttribute[] xmlAttributes = xmlNode.attributes();
        for (XMLAttribute xmlAttribute : xmlAttributes){
            String key = xmlAttribute.key();
            Attribute attribute = element.attribute(key);
            if (attribute == null){
                return VerifyFingerprintResp.FAIL;
            }
            String value = xmlAttribute.value();
            beanSign.append(key);
            elementSign.append(key);
            if (StringUtils.isNotBlank(value)){
                beanSign.append(value);
                elementSign.append(attribute.getValue());
            }else {
                temp = attribute.getValue();
            }
        }
        return VerifyFingerprintResp.build(beanSign.toString().equals(elementSign.toString()), temp);
    }
}
