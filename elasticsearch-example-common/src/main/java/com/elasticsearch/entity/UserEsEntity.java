package com.elasticsearch.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author young
 * @version 1.0
 * @date 2021/4/8 12:03 下午
 * @description
 */
@Data
public class UserEsEntity implements Serializable {
    private static final long serialVersionUID = 7405585886435411734L;
    private Integer id;
    private String name;
    private String email;
    private String address;
}
