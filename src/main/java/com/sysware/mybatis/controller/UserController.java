package com.sysware.mybatis.controller;

import com.sysware.mybatis.domain.JsonData;
import com.sysware.mybatis.domain.User;
import com.sysware.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 新增用户
     * @param user
     * @return
     */
    @PostMapping("add")
    public Object add(@RequestBody User user){
        return JsonData.buildSuccess(userService.add(user));
    }

    /**
     * 查询全部用户
     * @return
     */
    @GetMapping("list_all")
    public Object listAll(){
        return JsonData.buildSuccess(userService.listAll());
    }

    /**
     * 根据用户ID查询信息
     * @RequestParam用户指定接口传过来的参数，如果一致可以不设置
     * @param id
     * @return
     */
    @GetMapping("get_by_id")
    public Object getById(@RequestParam("id") long id){
        return JsonData.buildSuccess(userService.getById(id));
    }

    /**
     * 更新用户信息
     * @param name
     * @param id
     * @return
     */
    @PutMapping("update")
    public Object update(@RequestParam("name") String name, @RequestParam("id") int id){
        User user = new User();
        user.setId(id);
        user.setName(name);
        userService.update(user);
        return JsonData.buildSuccess();
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @DeleteMapping("remove_by_id")
    public Object delete(@RequestParam("id") long id){
        userService.delete(id);
        return JsonData.buildSuccess();
    }

    /**
     * 测试事务
     * @return
     */
    @GetMapping("test_transaction")
    public Object testTransaction(){
        return JsonData.buildSuccess(userService.testTransaction());
    }

}