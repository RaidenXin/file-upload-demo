package com.raiden.test.model;

import com.raiden.annotation.XMLAttribute;
import com.raiden.annotation.XMLNode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 21:58 2020/8/1
 * @Modified By:
 */
@XMLNode(nameOfTheNod = "MESSAGE",attributes = {
        @XMLAttribute(key = "BUSINESS_ID", value = "10001"),
        @XMLAttribute(key = "rmk", value = "报送信息")
})
@Setter
@Getter
public class Message {
    @XMLNode(nameOfTheNod = "DATASET")
    private List<ReportFormInfo> reportFormInfos;
    @XMLNode(nameOfTheNod = "DATASET")
    private List<User> users;

    @Override
    public String toString() {
        return "Message{" +
                "reportFormInfos=" + reportFormInfos.toString() +
                ", users=" + users.toString() +
                '}';
    }
}
