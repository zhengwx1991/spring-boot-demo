package com.sysware.mybatis.controller;

import com.sysware.mybatis.domain.JsonData;
import com.sysware.mybatis.domain.User;
import com.sysware.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @program: UserController
 * @description: 用户Controller
 * @author: zhengwx
 * @create: 2019-08-14 16:40
 * @version: 1.0.0
 **/
@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("add")
    public Object add(){
        User user = new User();
        user.setAge(20);
        user.setCreateTime(new Date());
        user.setName("小明");
        user.setPhone("13888888888");
        return JsonData.buildSuccess(userService.add(user));
    }

    @GetMapping("list_all")
    public Object listAll(){
        return JsonData.buildSuccess(userService.listAll());
    }

    @GetMapping("get_by_id")
    public Object getById(long id){
        return JsonData.buildSuccess(userService.getById(id));
    }

    @GetMapping("update")
    public Object update(String name,int id){
        User user = new User();
        user.setId(id);
        user.setName(name);
        userService.update(user);
        return JsonData.buildSuccess();
    }

    @GetMapping("remove_by_id")
    public Object delete(long id){
        userService.delete(id);
        return JsonData.buildSuccess();
    }

}
