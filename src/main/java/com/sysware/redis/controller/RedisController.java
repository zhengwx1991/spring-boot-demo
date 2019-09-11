package com.sysware.redis.controller;

import com.sysware.mybatis.domain.JsonData;
import com.sysware.mybatis.domain.User;
import com.sysware.mybatis.mapper.UserMapper;
import com.sysware.mybatis.service.UserService;
import com.sysware.redis.util.RedisConfig;
import com.sysware.redis.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: RedisController
 * @description: redis的controller
 * @author: zwx
 * @create: 2019-09-11 21:21
 **/
@RestController
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserService userService;

    private static final String key = "userCache_";

    /**
     * 测试redis的新增和查询
     *
     * @param key
     * @param value
     * @return
     */
    @GetMapping("api/v1/redis/get")
    public String setAndGet(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * 测试封装的util类
     * @param key
     * @param value
     * @return
     */
    @GetMapping("api/v1/redis/util")
    public String testUtil(String key, String value) {
        redisUtil.set(key, value);
        return (String) redisUtil.get(key);
    }

    /**
     * 注意redis持久化的set  get时序列化方式必须保持一致
     *
     * @param id
     * @return
     */
    @GetMapping("api/v1/redis/user")
    public Object getById(@RequestParam("id") long id) {
        // 1.先从redis中获取数据
        User user = (User) redisUtil.get(key + id);

        // 2.当redis中没有数据时，查询MySQL中的数据
        if (user == null) {
            user = userService.getById(id);

            // 3.当MySQL中查询到数据时，将数据写入redis中
            if (user != null) {
                redisUtil.set(key + user.getId(), user);
            }
        }
        return JsonData.buildSuccess(user);
    }

}
