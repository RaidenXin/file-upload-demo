package com.raiden.test.model;

import com.raiden.core.annotation.XMLAttribute;
import com.raiden.core.annotation.XMLNode;
import lombok.Getter;
import lombok.Setter;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 15:53 2020/3/21
 * @Modified By:
 */
@Setter
@Getter
@XMLNode(nameOfTheNod = "DATA")
public class User {
    @XMLNode(nameOfTheNod = "ITEM",
            attributes = {
            @XMLAttribute(key = "key", value = "ID"),
            @XMLAttribute(key = "val"),
            @XMLAttribute(key = "rmk", value = "帐号")
    })
    private String id;
    @XMLNode(nameOfTheNod = "ITEM",
            attributes = {
            @XMLAttribute(key = "key", value = "NAME"),
            @XMLAttribute(key = "val"),
            @XMLAttribute(key = "rmk", value = "联系人")
    })
    private String name;
    @XMLNode(nameOfTheNod = "ITEM",
            attributes = {
            @XMLAttribute(key = "key", value = "STUDENT"),
            @XMLAttribute(key = "val"),
            @XMLAttribute(key = "rmk", value = "班级")
    })
    private String student;
    @XMLNode(nameOfTheNod = "ITEM",
//            serialization = @XMLSerialization(isDataConversion = true, dateFormatFunctions = LocalDateUtils.GET_TIME),
//            deserialization = @XMLDeserialization(isDataConversion = true, dateFormatFunctions = LocalDateUtils.FORMAT),
            attributes = {
            @XMLAttribute(key = "key", value = "BIRTHDAY"),
            @XMLAttribute(key = "val"),
            @XMLAttribute(key = "rmk", value = "生日")
    })
    private String dateOfBirth;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", student='" + student + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                '}';
    }
}
