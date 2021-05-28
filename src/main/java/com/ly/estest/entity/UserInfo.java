package com.ly.estest.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author ：LY
 * @date ：2021/4/23 14:03
 * @description ：UserInfo
 */
@Data
public class UserInfo {
    private String name;
    private String address;
    private String remark;
    private Integer age;
    private Float salary;
    private Date createTime;
    private String birthDate;
}
