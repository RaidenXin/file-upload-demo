package com.raiden.core.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 17:43 2020/8/2
 * @Modified By:
 */
@Setter
@Getter
public class VerifyFingerprintResp {

    private boolean success;
    private String value;

    public static final VerifyFingerprintResp FAIL = new VerifyFingerprintResp();

    public static final VerifyFingerprintResp build(boolean success,String value){
        VerifyFingerprintResp resp = new VerifyFingerprintResp();
        resp.success = success;
        resp.value = value;
        return resp;
    }
}
