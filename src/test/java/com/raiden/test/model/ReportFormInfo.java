package com.raiden.test.model;

import com.raiden.annotation.XMLAttribute;
import com.raiden.annotation.XMLNode;
import lombok.Getter;
import lombok.Setter;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 22:02 2020/8/1
 * @Modified By:
 */
@XMLNode(nameOfTheNod = "DATA")
@Getter
@Setter
public class ReportFormInfo {

    @XMLNode(nameOfTheNod = "ITEM",attributes = {
            @XMLAttribute(key = "key", value = "BUSINESS_ID"),
            @XMLAttribute(key = "value"),
            @XMLAttribute(key = "rmk", value = "业务ID")
    })
    private String id;
    @XMLNode(nameOfTheNod = "ITEM",attributes = {
            @XMLAttribute(key = "key", value = "SERIAL_NUMBER"),
            @XMLAttribute(key = "value"),
            @XMLAttribute(key = "rmk", value = "业务ID")
    })
    private long serialNumber;


    @Override
    public String toString() {
        return "ReportFormInfo{" +
                "id='" + id + '\'' +
                ", serialNumber=" + serialNumber +
                '}';
    }
}
