package com.sysware.redis.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @program: RedisConfig
 * @description: redis连接工具配置类
 * @author: zwx
 * @create: 2019-09-11 21:12
 **/
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, String> redisTemplate (RedisConnectionFactory factory){
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }
}
