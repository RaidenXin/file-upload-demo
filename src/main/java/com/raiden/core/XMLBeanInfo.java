package com.raiden.core;

import lombok.Getter;
import lombok.Setter;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 12:42 2020/8/2
 * @Modified By:
 */
@Getter
@Setter
public final class XMLBeanInfo {

    private FieldInfo[] fieldInfos;

    public XMLBeanInfo(){
        this(new FieldInfo[0]);
    }

    public XMLBeanInfo(FieldInfo[] fieldInfos){
        this.fieldInfos = fieldInfos;
    }
}
