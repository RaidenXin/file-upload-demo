package com.raiden.test;

import com.raiden.test.model.User;
import com.raiden.util.XMLUtils;
import com.raiden.util.ZipUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Function;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 23:31 2020/7/30
 * @Modified By:
 */
public class Start {

    private static final String ZIP_PATH = "C:\\Users\\Raiden\\Desktop\\user";
    private static final String PATH = "user.xml";

    @Test
    public void test(){
        User user = new User();
        user.setId("1");
        user.setName("张三");
        user.setStudent("高三三班");
        user.setDateOfBirth("2015年1月1日");
        File file = XMLUtils.toXMLFile(user, PATH);
        try {
            ZipUtils.writeZip(Arrays.asList(file), ZIP_PATH);
        } catch (Exception e) {
        }
    }

    @Test
    public void test2() throws NoSuchFieldException, IllegalAccessException {
        User user = new User();
        String[] array = {"1", "2"};
        Function<HashSet<String>, Map<String, String>> test = com.raiden.test.Test::test;
        Field field = User.class.getDeclaredField("array");
        field.setAccessible(true);
        Object[] os = (Object[]) field.get(user);
        for (Object ob : os){
            System.err.println(ob);
        }
    }
}
