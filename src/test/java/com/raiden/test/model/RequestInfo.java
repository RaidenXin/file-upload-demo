package com.raiden.test.model;

import com.raiden.core.annotation.XMLAttribute;
import com.raiden.core.annotation.XMLNode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@XMLNode(nameOfTheNod = "MESSAGE")
@Getter
@Setter
public class RequestInfo {

    @XMLNode(nameOfTheNod = "DATASET",attributes = {
            @XMLAttribute(key = "name", value = "WA_COMMON_010000"),
            @XMLAttribute(key = "rmk", value = "消息通用信息")
    })
    private ArrayList<RequestInfoData> dataSet;

    @Override
    public String toString() {
        return "RequestInfo{" +
                "dataSet=" + dataSet.toString() +
                '}';
    }
}
