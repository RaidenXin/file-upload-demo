package com.raiden;

import com.alibaba.fastjson.JSON;
import com.raiden.model.User;
import com.raiden.util.LocalDateUtils;
import com.raiden.util.XMLUtils;
import com.raiden.util.ZipUtils;
import org.junit.jupiter.api.Test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 23:31 2020/7/30
 * @Modified By:
 */
public class Start {

    private static final String ZIP_PATH = "C:\\Users\\xinlei002\\Desktop\\user";

    @Test
    public void test(){
        User user = new User();
        user.setId("1");
        user.setName("张三");
        user.setStudent("高三三班");
        user.setDateOfBirth("2015年1月1日");
        try {
            File file = XMLUtils.serializeToXML(user);
            ZipUtils.writeZip(Arrays.asList(file), ZIP_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() throws NoSuchFieldException, IllegalAccessException {
        User user = new User();
        String[] array = {"1", "2"};
        Field field = User.class.getDeclaredField("array");
        field.setAccessible(true);
        Object[] os = (Object[]) field.get(user);
        for (Object ob : os){
            System.err.println(ob);
        }
    }
}
