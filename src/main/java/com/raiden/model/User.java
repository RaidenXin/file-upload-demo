package com.raiden.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 15:53 2020/3/21
 * @Modified By:
 */
@Getter
@Setter
public class User {
    private String id;
    private String student;
    private List<String> hobby;
}
