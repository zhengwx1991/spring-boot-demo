package com.sysware.http.controller;

import com.sysware.mybatis.domain.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: GetController
 * @description: get请求
 * @author: zhengwx
 * @create: 2019-08-15 09:55
 * @version: 1.0.0
 **/
@RestController
@RequestMapping("api/v1/get")
public class GetController {

    private Map<String, Object> params = new HashMap<>();

    /**
     * 测试restful协议，从路径中获取参数字段
     * 初级版
     * @param cityId
     * @param userId
     * @return
     */
    @RequestMapping(path = "/{city_id}/{user_id}", method = RequestMethod.GET)
    public Object findUser(@PathVariable("city_id") String cityId, @PathVariable("user_id") String userId) {
        params.clear();
        params.put("cityId", cityId);
        params.put("userId", userId);
        return params;
    }

    /**
     * 测试GetMapping
     * @param from
     * @param size
     * @return
     */
    @GetMapping("page_user1")
    public Object pageUser1(int from, int size){
        params.clear();
        params.put("from", from);
        params.put("size", size);
        return params;
    }

    /**
     * 默认值，如果接口没传该参数，则使用默认值
     * 参数是否必填验证（不是强项,可以由前端或者拦截器等验证）
     * page是前端传过来的对应值，也可以不设置
     * @param from
     * @param size
     * @return
     */
    @GetMapping("page_user2")
    public Object pageUser2(@RequestParam(defaultValue = "0", name = "page", required = true) int from, int size){
        params.clear();
        params.put("from", from);
        params.put("size", size);
        return params;
    }

    /**
     * bean对象传参
     * 注意：1、注意需要指定http头为content-type为application/json
     * 2、使用body传输数据
     * 3、只能用post提交，所以注解不是@GetMapping而是@RequestMapping
     * @param user
     * @return
     */
    @RequestMapping("save_user")
    public Object saveUser(@RequestBody User user){
        params.clear();
        params.put("user", user);
        return params;
    }

    /**
     * 获取http请求头信息，使用场景比如鉴权
     * @param accessToken
     * @param id
     * @return
     */
    @GetMapping("get_header")
    public Object getHeader(@RequestHeader("access_token") String accessToken, String id){
        params.clear();
        params.put("accessToken", accessToken);
        params.put("id", id);
        return params;
    }


}