package com.sysware.redis.service;

import com.sysware.mybatis.domain.User;
import com.sysware.mybatis.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * 描述：redis作为缓存service
 * 本类内方法指定使用缓存时，默认的名称就是redis_service
 *
 * @Author: zwx
 * @Company 云迹科技
 * @Date: 2019/10/10 10:29
 */
@Service
@CacheConfig(cacheNames = "redis_service")
@Transactional(rollbackFor = Exception.class)
public class RedisServiceImpl implements RedisService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapper userMapper;

    /**
     * 因为必须要有返回值，才能保存到数据库中，如果保存的对象的某些字段是需要数据库生成的
     * 那保存对象进数据库的时候，就没必要放到缓存了
     * 必须要有返回值，否则没数据放到缓存中
     * #p0表示第一个参数
     */
    @Override
    @CachePut(key = "#p0.id")
    public User saveUser(User user) {
        this.userMapper.insert(user);
        // user对象中可能只有只几个有效字段，其他字段值靠数据库生成，比如id
        return this.userMapper.getById(user.getId());
    }

    @Override
    @CachePut(key = "#p0.id")
    public User updateUser(User user) {
        this.userMapper.update(user);
        // 可能只是更新某几个字段而已，所以查次数据库把数据全部拿出来全部
        return this.userMapper.getById(user.getId());
    }

    @Override
    public User getMysqlById(int id) {
        return this.userMapper.getById(id);
    }

    /**
     * @Cacheable 会先查询缓存，如果缓存中存在，则不执行方法
     */
    @Override
    @Nullable
    @Cacheable(key = "#p0")
    public User getUserById(int id) {
        logger.error("根据id=" + id + "获取用户对象，从数据库中获取");
        Assert.notNull(id, "id不用为空");
        return this.userMapper.getById(id);
    }

    /**
     * 注意：
     * @Cacheable(value = "redis_service", keyGenerator = "simpleKeyGenerator")
     * 区别于上边的@Cacheable(key = "#p0")
     * 其中key需要指定RedisConfig类中的key生成方法
     * value需要指定RedisConfig类中的kgetRedisCacheConfigurationMap方法中的对应值，由此来确定超时时间
     */
    @Override
    @Nullable
    @Cacheable(value = "redis_service", keyGenerator = "simpleKeyGenerator")
    public User getUserByIdTimeout(int id) {
        logger.error("根据id=" + id + "获取用户对象，从数据库中获取");
        Assert.notNull(id, "id不用为空");
        return this.userMapper.getById(id);
    }

    /**
     * 删除缓存名称为userInfoCache,key等于指定的id对应的缓存
     */
    @Override
    @CacheEvict(key = "#p0")
    public void deleteUserById(int id) {
        this.userMapper.delete(id);
    }

    /**
     * 清空缓存名称为redis_service（看类名上的注解)下的所有缓存
     * 如果数据失败了，缓存时不会清除的
     */
    @Override
    @CacheEvict(allEntries = true)
    public void deleteUserAll() {

    }
}
