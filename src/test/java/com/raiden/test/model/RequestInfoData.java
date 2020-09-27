package com.raiden.test.model;

import com.raiden.core.annotation.XMLAttribute;
import com.raiden.core.annotation.XMLNode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XMLNode(nameOfTheNod = "DATA")
public class RequestInfoData {

    @XMLNode(nameOfTheNod = "ITEM",
            attributes = {
                    @XMLAttribute(key = "key", value = "APPTYPE"),
                    @XMLAttribute(key = "val"),
                    @XMLAttribute(key = "rmk", value = "应用编码，字典码")
    })
    private String appType;
    @XMLNode(nameOfTheNod = "ITEM",
            attributes = {
                    @XMLAttribute(key = "key", value = "OPCODE"),
                    @XMLAttribute(key = "val"),
                    @XMLAttribute(key = "rmk", value = "业务类型")
            })
    private String opcode;
    @XMLNode(nameOfTheNod = "ITEM",
            attributes = {
                    @XMLAttribute(key = "key", value = "MSGID"),
                    @XMLAttribute(key = "val"),
                    @XMLAttribute(key = "rmk", value = "消息流水号")
            })
    private String msgId;
    @XMLNode(nameOfTheNod = "ITEM",
            attributes = {
                    @XMLAttribute(key = "key", value = "MSGTYPE"),
                    @XMLAttribute(key = "val"),
                    @XMLAttribute(key = "rmk", value = "消息类型，1 请求，2 应答，3 结果")
            })
    private String msgType;
    @XMLNode(nameOfTheNod = "ITEM",
            attributes = {
                    @XMLAttribute(key = "key", value = "OPID"),
                    @XMLAttribute(key = "val"),
                    @XMLAttribute(key = "rmk", value = "业务编号")
            })
    private String opId;

    @Override
    public String toString() {
        return "RequestInfoData{" +
                "appType='" + appType + '\'' +
                ", opcode='" + opcode + '\'' +
                ", msgId='" + msgId + '\'' +
                ", msgType='" + msgType + '\'' +
                ", opId='" + opId + '\'' +
                '}';
    }
}
