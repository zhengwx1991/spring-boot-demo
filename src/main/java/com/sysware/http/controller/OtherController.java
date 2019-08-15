package com.sysware.http.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: OtherController
 * @description: 其他请求方式
 * @author: zhengwx
 * @create: 2019-08-15 10:37
 * @version: 1.0.0
 **/
@RestController
@RequestMapping("api/v1/other")
public class OtherController {

    private Map<String, Object> params = new HashMap<>();

    /**
     * post提交，一般用于登陆，表单提交等
     * @param id
     * @param pwd
     * @return
     */
    @PostMapping("post")
    public Object post(String id, String pwd){
        params.clear();
        params.put("id", id);
        params.put("pwd", pwd);
        return params;
    }

    /**
     * put提交，一般用于更新
     * @param id
     * @return
     */
    @PutMapping("put")
    public Object put(String id){
        params.clear();
        params.put("id", id);
        return params;
    }

    /**
     * DeleteMapping提交，删除
     * @param id
     * @return
     */
    @DeleteMapping("del")
    public Object del(String id){
        params.clear();
        params.put("id", id);
        return params;
    }

}
