package com.sysware.redis.controller;

import com.sysware.mybatis.domain.JsonData;
import com.sysware.mybatis.domain.User;
import com.sysware.mybatis.mapper.UserMapper;
import com.sysware.mybatis.service.UserService;
import com.sysware.redis.service.RedisService;
import com.sysware.redis.util.RedisConfig;
import com.sysware.redis.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @program: RedisController
 * @description: redis的controller
 * @author: zwx
 * @create: 2019-09-11 21:21
 **/
@RestController
@RequestMapping("api/v1/redis")
@Api(value = "redis相关API", tags = {"redis操作接口"})
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    private static final String key = "userCache_";

    /**
     * 测试redis的新增和查询
     *
     * @param key
     * @param value
     * @return
     */
    @GetMapping("get")
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
    @GetMapping("util")
    public String testUtil(String key, String value) {
        redisUtil.set(key, value);
        return (String) redisUtil.get(key);
    }

    /**
     * 注意redis持久化的set  get时序列化方式必须保持一致
     * redis默认的为JdkSerializationRedisSerializer，所以实体类也要继承
     * @param id
     * @return
     */
    @ApiOperation(value = "根据用户ID查询", notes = "")
    @GetMapping("user")
    public Object getById(@RequestParam("id") int id) {
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

    /**
     * 新增用户
     * @param user
     * @return
     */
    @ApiOperation(value = "新增用户", notes = "新增用户注意事项")
    @PostMapping("add")
    public Object add(@RequestBody User user){
        return JsonData.buildSuccess(redisService.saveUser(user));
    }

    /**
     * 功能描述: 根据用户ID查询信息，直连数据库
     * @param id
     * @return: java.lang.Object
     * @auther: zwx
     * @date: 2019/10/10 7:40 下午
     */
    @ApiOperation(value = "根据用户ID查询信息，直连数据库", notes = "")
    @GetMapping("get_mysql_by_id")
    public Object getMysqlById(@RequestParam("id") int id){
        return JsonData.buildSuccess(redisService.getMysqlById(id));
    }

    /**
     * 根据用户ID查询信息
     * @RequestParam用户指定接口传过来的参数，如果一致可以不设置
     * @param id
     * @return
     */
    @ApiOperation(value = "根据用户ID查询信息", notes = "")
    @GetMapping("get_redis_by_id")
    public Object getRedisById(@RequestParam("id") int id){
        return JsonData.buildSuccess(redisService.getUserById(id));
    }

    /**
     * 功能描述:
     * @param id
     * @return: java.lang.Object
     * @auther: zwx
     * @date: 2019/10/10 6:51 下午
     */
    @ApiOperation(value = "根据用户ID查询信息,设置了redis的超时时间", notes = "")
    @GetMapping("get_redis_by_id_timeout")
    public Object getUserByIdTimeout(@RequestParam("id") int id){
        return JsonData.buildSuccess(redisService.getUserByIdTimeout(id));
    }

    /**
     * 更新用户信息
     * @param name
     * @param id
     * @return
     */
    @ApiOperation(value = "更新用户信息", notes = "")
    @PutMapping("update")
    public Object update(@RequestParam("name") String name, @RequestParam("id") int id){
        User user = new User();
        user.setId(id);
        user.setName(name);
        redisService.updateUser(user);
        return JsonData.buildSuccess();
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @ApiOperation(value = "删除用户", notes = "")
    @DeleteMapping("remove_by_id")
    public Object delete(@RequestParam("id") int id){
        redisService.deleteUserById(id);
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
