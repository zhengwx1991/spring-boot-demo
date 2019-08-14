package com.sysware.mybatis.domain;

import java.util.Date;

/**
 * @program: User
 * @description: 用户类
 * @author: zhengwx
 * @create: 2019-08-14 15:13
 * @version: 1.0.0
 **/
public class User {

    private int id;

    private String name;

    private String phone;

    private int age;

    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
