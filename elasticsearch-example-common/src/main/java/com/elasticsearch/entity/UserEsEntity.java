package com.elasticsearch.entity;

import lombok.Data;

/**
 * @author young
 * @version 1.0
 * @date 2021/4/8 12:03 下午
 * @description
 */
@Data
public class UserEsEntity {
    private Integer id;
    private String name;
    private String email;
}
