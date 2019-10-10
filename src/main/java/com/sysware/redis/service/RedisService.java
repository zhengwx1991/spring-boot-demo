package com.sysware.redis.service;

import com.sysware.mybatis.domain.User;

/**
 * 描述:reids作为缓存service
 * @Author: zwx
 * @Company 云迹科技
 * @Date: 2019/10/10 10:30 上午
 */
public interface RedisService {

    /**
     * 功能描述: 新增用户
     * @param user
     * @return: com.sysware.mybatis.domain.User
     * @auther: zwx
     * @date: 2019/10/10 11:04 上午
     */
    User saveUser(User user);

    /**
     * 功能描述: 更新用户
     * @param user
     * @return: com.sysware.mybatis.domain.User
     * @auther: zwx
     * @date: 2019/10/10 11:03 上午
     */
    User updateUser(User user);

    /**
     *
     * 功能描述: 根据ID查找用户，未设置超时时间
     * @param id
     * @return: com.sysware.mybatis.domain.User
     * @auther: zwx
     * @date: 2019/10/10 11:02 上午
     */
    User getMysqlById(int id);

    /**
     *
     * 功能描述: 根据ID查找用户，未设置超时时间
     * @param id
     * @return: com.sysware.mybatis.domain.User
     * @auther: zwx
     * @date: 2019/10/10 11:02 上午
     */
    User getUserById(int id);

    /**
     * 功能描述:  获取用户信息，设置了超时时间
     * @param id
     * @return: com.sysware.mybatis.domain.User
     * @auther: zwx
     * @date: 2019/10/10 6:45 下午
     */
    User getUserByIdTimeout(int id);

    /**
     *
     * 功能描述:
     * @param id
     * @return: void
     * @auther: zwx
     * @date: 2019/10/10 11:02 上午
     */;
    void deleteUserById(int id);

    /**
     * 功能描述: 删除全部用户
     * @return: void
     * @auther: zwx
     * @date: 2019/10/10 11:22 上午
     */
    void deleteUserAll();
}