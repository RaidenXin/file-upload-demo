package com.raiden;

import com.raiden.model.User;
import com.raiden.util.XMLUtils;
import com.raiden.util.ZipUtils;
import org.junit.jupiter.api.Test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 23:31 2020/7/30
 * @Modified By:
 */
public class Start {

    private static final String PATH = "user.xml";
    private static final String ZIP_PATH = "C:\\Users\\Raiden\\Desktop\\user";
    private static final String CHARACTER_SET = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

    @Test
    public void test(){
        User user = new User();
        user.setId("1");
        user.setStudent("高三三班");
        user.setHobby(Arrays.asList("足球","篮球"));
        String xml = CHARACTER_SET + XMLUtils.serializeToXML(user);
        File file = new File(PATH);
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))){
            outputStream.write(xml.getBytes(Charset.forName("UTF-8")));
            outputStream.flush();
            ZipUtils.writeZip(Arrays.asList(file), ZIP_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            file.delete();
        }
    }
}
