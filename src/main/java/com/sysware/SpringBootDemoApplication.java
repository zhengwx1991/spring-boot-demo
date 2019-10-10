package com.sysware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 描述: 项目启动类
 * @Author: zwx
 * @Company 云迹科技
 * @Date: 2019/10/10 5:22 下午
 * @SpringBootApplication   springboot启动注解
 * @EnableScheduling        开启定时任务
 * @EnableAsync             开启异步任务
 * @MapperScan              扫描mapper
 * @EnableSwagger2          开启Swagger
 */
@SpringBootApplication
@EnableScheduling
@EnableAsync
@MapperScan("com.sysware.mybatis.mapper")
@EnableSwagger2
public class SpringBootDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }
}
