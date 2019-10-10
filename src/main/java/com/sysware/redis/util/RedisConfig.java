package com.sysware.redis.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: RedisConfig
 * @description: redis连接工具配置类
 * @EnableCaching 开启缓存
 * @author: zwx
 * @create: 2019-09-11 21:12
 **/
@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    /**
     * 功能描述: 用来生成redis的key，防止key过于简单导致的重复
     * 生成规则：类名+方法名+参数   Java虚拟机也是通过这种方式去区分的
     * @return: org.springframework.cache.interceptor.KeyGenerator
     * @auther: zwx
     * @date: 2019/10/10 5:54 下午
     */
    @Bean
    public KeyGenerator simpleKeyGenerator() {
        return (o, method, objects) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(o.getClass().getName());
            sb.append(".");
            sb.append(method.getName());
            sb.append("[");
            for (Object obj : objects) {
                sb.append(obj.toString());
            }
            sb.append("]");
            return sb.toString();
        };
    }

    /**
     * 功能描述:  设置缓存超时时间
     * @param redisConnectionFactory
     * @return: org.springframework.cache.CacheManager
     * @auther: zwx
     * @date: 2019/10/10 6:42 下午
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return new RedisCacheManager(
                RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
                // 默认策略，未配置的 key 会使用这个
                this.getRedisCacheConfigurationWithTtl(600),
                // 指定 key 策略
                this.getRedisCacheConfigurationMap()
        );
    }

    /**
     * 功能描述: 缓存时间存放池，存放不同配置的过期时间
     * @return: java.util.Map<java.lang.String,org.springframework.data.redis.cache.RedisCacheConfiguration>
     * @auther: zwx
     * @date: 2019/10/10 6:37 下午
     */
    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        // 可以自定义不同的超时时间
        redisCacheConfigurationMap.put("redis_service", this.getRedisCacheConfigurationWithTtl(100));
        redisCacheConfigurationMap.put("UserInfoListAnother", this.getRedisCacheConfigurationWithTtl(1800));
        return redisCacheConfigurationMap;
    }

    /**
     * 功能描述:
     * @param seconds
     * @return: org.springframework.data.redis.cache.RedisCacheConfiguration
     * @auther: zwx
     * @date: 2019/10/10 6:05 下午
     */
    private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds) {
        // 指定序列化方式
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer)
        ).entryTtl(Duration.ofSeconds(seconds));
        return redisCacheConfiguration;
    }
}