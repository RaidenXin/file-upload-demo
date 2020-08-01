package com.raiden.test.model;

import com.raiden.annotation.XMLAttribute;
import com.raiden.annotation.XMLNode;
import com.raiden.util.LocalDateUtils;
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
    @XMLNode(nameOfTheNod = "ITEM2",
            attributes = {
            @XMLAttribute(key = "key", value = "ID"),
            @XMLAttribute(key = "val"),
            @XMLAttribute(key = "rmk", value = "帐号")
    })
    private String id;
    @XMLNode(nameOfTheNod = "ITEM3",
            attributes = {
            @XMLAttribute(key = "key", value = "NAME"),
            @XMLAttribute(key = "val"),
            @XMLAttribute(key = "rmk", value = "联系人")
    })
    private String name;
    @XMLNode(nameOfTheNod = "ITEM4",
            attributes = {
            @XMLAttribute(key = "key", value = "STUDENT"),
            @XMLAttribute(key = "val"),
            @XMLAttribute(key = "rmk", value = "班级")
    })
    private String student;
    @XMLNode(nameOfTheNod = "ITEM5",isDataConversion = true,
            dateFormatFunction = LocalDateUtils.GET_TIME,
            attributes = {
            @XMLAttribute(key = "key", value = "BIRTHDAY"),
            @XMLAttribute(key = "val"),
            @XMLAttribute(key = "rmk", value = "生日")
    })
    private String dateOfBirth;
}
