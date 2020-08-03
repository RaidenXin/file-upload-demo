package com.raiden.test;

import com.raiden.test.model.Message;
import com.raiden.test.model.ReportFormInfo;
import com.raiden.test.model.User;
import com.raiden.core.util.XMLUtils;
import com.raiden.core.util.ZipUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 23:31 2020/7/30
 * @Modified By:
 */
public class Start {

    private static final String ZIP_PATH = "C:\\Users\\Raiden\\Desktop\\message.zip";
    private static final String PATH = "user.xml";

    public static Message getMessage(){
        Message message = new Message();
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setId("1");
        user.setName("张三");
        user.setStudent("高三三班");
        user.setDateOfBirth("2015年1月1日 1点:1分:1秒");
        users.add(user);
        User user2 = new User();
        user2.setId("2");
        user2.setName("李四");
        user2.setStudent("高三三班");
        user2.setDateOfBirth("1991年1月1日");
        users.add(user2);
        User user3 = new User();
        user3.setId("3");
        user3.setName("王二");
        user3.setStudent("高三三班");
        user3.setDateOfBirth("1991年2月2日");
        users.add(user3);
        User user4 = new User();
        user4.setId("4");
        user4.setName("钱六");
        user4.setStudent("高三三班");
        user4.setDateOfBirth("1991年3月3日");
        users.add(user4);
        ReportFormInfo formInfo = new ReportFormInfo();
        formInfo.setId(UUID.randomUUID().toString());
        formInfo.setSerialNumber(System.currentTimeMillis());
        List<ReportFormInfo> reportFormInfos = new ArrayList<>();
        reportFormInfos.add(formInfo);
        message.setUsers(users);
        message.setReportFormInfos(reportFormInfos);
        return message;
    }
    @Test
    public void test(){
        File file = XMLUtils.toXMLFile(getMessage(), PATH);
        try {
            ZipUtils.writeZip(Arrays.asList(file), ZIP_PATH);
        } catch (Exception e) {
        }
    }

    @Test
    public void test2() throws NoSuchFieldException, IllegalAccessException {
        User.class.getConstructors();
        System.err.println(XMLUtils.serialize(getMessage()));
    }
    @Test
    public void test3() throws NoSuchFieldException, IllegalAccessException {
        Message message = getMessage();
        String xml = XMLUtils.serialize(message);
        System.out.println(xml);
        Message deserialize = XMLUtils.deserialize(xml, Message.class);
        System.err.println(message.toString());
        System.out.println(deserialize.toString());
        System.err.println(message.toString().equals(deserialize.toString()));
    }

    @Test
    public void test4() throws NoSuchFieldException {
        System.out.println(new Date().getTime());
        System.out.println(new Date().getTime() * 1000L);
    }
}
