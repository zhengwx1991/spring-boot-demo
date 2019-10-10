package com.sysware.mybatis.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: User
 * @description: 用户类
 * @author: zhengwx
 * @create: 2019-08-14 15:13
 * @version: 1.0.0
 **/
@Getter
@Setter
@ToString
public class User implements Serializable {

    private static final long serialVersionUID = -4415438719697624729L;

    private int id;

    private String name;

    private String phone;

    private int age;

    private Date createTime;

}
