### 该项目为SpringBoot的Demo项目，相关的技术Demo均写在该项目下
### 定时任务

###### 常见定时任务

	1、 Java自带的java.util.Timer类
	timer:配置比较麻烦，时间延后问题
	timertask:不推荐
	
	2、Quartz框架
	配置更简单
	xml或者注解
	
	3、SpringBoot自带的schedule
###### SpringBoot使用注解方式开启定时任务

1）启动类里面 @EnableScheduling开启定时任务，自动扫描

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
// 启动类里面 @EnableScheduling开启定时任务，自动扫描
@EnableScheduling
public class SpringBootDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }
}
```

2）定时任务业务类 加注解 @Component被容器扫描

3）定时执行的方法加上注解 @Scheduled(fixedRate=2000) 定期执行一次

```
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class TimingTask {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Scheduled(fixedRate = 200000)
    public void start(){
        logger.info("执行了定时任务，时间："+new Date());
    }
}
```

###### SpringBoot常用定时任务配置

```
定时任务表达式配置和在线生成器
	1、cron 定时任务表达式 @Scheduled(cron="*/1 * * * * *") 表示每秒
		1）crontab 工具  https://tool.lu/crontab/
	2、fixedRate: 定时多久执行一次（上一次开始执行时间点后xx秒再次执行；）
	3、fixedDelay: 上一次执行结束时间点后xx秒再次执行
	4、fixedDelayString:  字符串形式，可以通过配置文件指定
```

### 异步任务

###### 什么是异步任务和使用场景

适用于处理log、发送邮件、短信……等

启动类里面使用@EnableAsync注解开启功能，自动扫描

```java
@SpringBootApplication
// 开启异步任务
@EnableAsync
public class SpringBootDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }
}
```

定义异步任务类并使用@Component标记组件被容器扫描,异步方法加上@Async

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Component
@Async
public class AsyncTask {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void task1() throws InterruptedException {
        long start = System.currentTimeMillis();
        // 休眠
        TimeUnit.SECONDS.sleep(1);
        long end = System.currentTimeMillis();
        logger.info("执行了异步任务1，时间："+(end-start));
    }

    public void task2() throws InterruptedException {
        long start = System.currentTimeMillis();
        TimeUnit.SECONDS.sleep(2);
        long end = System.currentTimeMillis();
        logger.info("执行了异步任务2，时间："+(end-start));
    }

    public void task3() throws InterruptedException {
        long start = System.currentTimeMillis();
        TimeUnit.SECONDS.sleep(3);
        long end = System.currentTimeMillis();
        logger.info("执行了异步任务3，时间："+(end-start));
    }

    // 以下三个方法为：获取异步结果
    public Future<String> task4() throws InterruptedException {
        long start = System.currentTimeMillis();
        TimeUnit.SECONDS.sleep(2);
        long end = System.currentTimeMillis();
        String msg = "执行了异步任务4，时间："+(end-start);
        logger.info(msg);
        return new AsyncResult<String>(msg);
    }

    public Future<String> task5() throws InterruptedException {
        long start = System.currentTimeMillis();
        TimeUnit.SECONDS.sleep(3);
        long end = System.currentTimeMillis();
        String msg = "执行了异步任务5，时间："+(end-start);
        logger.info(msg);
        return new AsyncResult<String>(msg);
    }

    public Future<String> task6() throws InterruptedException {
        long start = System.currentTimeMillis();
        TimeUnit.SECONDS.sleep(1);
        long end = System.currentTimeMillis();
        String msg = "执行了异步任务6，时间："+(end-start);
        logger.info(msg);
        return new AsyncResult<String>(msg);
    }
}
```

调用异步任务

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.Future;

@RestController
@RequestMapping("api/v1/async")
public class AsyncTaskController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AsyncTask asyncTask;

    /**
     * 调用异步任务
     * @return
     * @throws InterruptedException
     */
    @RequestMapping("async_task")
    public Object asyncTask() throws InterruptedException {
        long start = System.currentTimeMillis();
        asyncTask.task1();
        asyncTask.task2();
        asyncTask.task3();
        long end = System.currentTimeMillis();
        logger.info("执行总耗时："+(end-start));
        return "异步任务";
    }

    /**
     * 调用异步任务，并获取异步任务的结果
     * @return
     * @throws InterruptedException
     */
    @RequestMapping("async_task_result")
    public Object asyncTaskResult() throws InterruptedException {
        long start = System.currentTimeMillis();
        Future<String> task4 = asyncTask.task4();
        Future<String> task5 = asyncTask.task5();
        Future<String> task6 = asyncTask.task6();
        // 死循环
        for (;;){
            // 判断这三个任务是否全部完成，如果完成则跳出循环
            if (task4.isDone() && task5.isDone() && task6.isDone()){
                break;
            }
        }
        long end = System.currentTimeMillis();
        logger.info("执行总耗时："+(end-start));
        return "异步任务";
    }
}
```

注意点：

```
1）要把异步任务封装到类里面，不能直接写到Controller
2）增加Future<String> 返回结果 AsyncResult<String>("task执行完成");  
3）如果需要拿到结果 需要判断全部的 task.isDone()
4、通过注入方式，注入到controller里面，如果测试前后区别则改为同步则把Async注释掉
```
------
### 自定义banner官方文档

https://docs.spring.io/spring-boot/docs/2.1.0.BUILD-SNAPSHOT/reference/htmlsingle/#boot-features-banners

### banner生成网站

https://www.bootschool.net/ascii

### 修改application.yml

```
spring:
  banner:
    # 个性化banner文件地址
    location: banner.txt
```

### 在resources目录下增加banner.txt

在banner.txt写入自己需要自定义的banner，如下：

```
  ____   __   __  ____   __        __     _      ____    _____
 / ___|  \ \ / / / ___|  \ \      / /    / \    |  _ \  | ____|
 \___ \   \ V /  \___ \   \ \ /\ / /    / _ \   | |_) | |  _|
  ___) |   | |    ___) |   \ V  V /    / ___ \  |  _ <  | |___
 |____/    |_|   |____/     \_/\_/    /_/   \_\ |_| \_\ |_____|
======================= 索为系统欢迎您 ! =======================
```
------
### 热部署官方文档
[https://docs.spring.io/spring-boot/docs/2.1.0.BUILD-SNAPSHOT/reference/htmlsingle/#using-boot-devtools](https://docs.spring.io/spring-boot/docs/2.1.0.BUILD-SNAPSHOT/reference/htmlsingle/#using-boot-devtools)

<!-- more -->

### pom文件添加依赖
	<dependencies>
		<!--热部署-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<optional>true</optional>
		</dependency>
	</dependencies>
	
	<build>
	    <plugins>
	        <plugin>
	            <groupId>org.springframework.boot</groupId>
	            <artifactId>spring-boot-maven-plugin</artifactId>
				<!--特别注意：配置devtools-->
				<!--如果没有该项配置，这个devtools不会起作用，即应用不会restart-->
	            <configuration>
	                <fork>true</fork>
	            </configuration>
	        </plugin>
	    </plugins>
	</build>
完成以上步骤即可使用热部署
### 个性化配置
	1、修改application.yml配置文件
		spring:
		  devtools:
		    restart:
		      #热部署生效，默认为true
		      enabled: true
		      #设置重启的目录
		      additional-paths: src/main/java
		      #classpath目录下的WEB-INF文件夹内容修改不重启
		      exclude: WEB-INF/**
		      # 指定触发文件，当该文件修改时才会重启
		      trigger-file: trigger.txt
	2、在src\main\resources创建trigger.txt文件写入version=1，每次需要重启系统时，修改该文件里边的数字即可，一是避免每次保存后台文件时系统频繁重启的问题，二是方便自己版本管理，如果不需要则trigger.txt文件里边的内容自定义即可。
### IDEA下不生效的问题
	1、Ctrl+Alt+S打开Settings
		找到Build, Execution, Deployment-->>Compiler
			勾选Build project automatically
![idea-setting-1.jpg](https://i.loli.net/2019/08/12/4K2shCTjDHtbk7a.png)

​	2、Ctrl+Shift+Alt+/或者Alt+Shift+A
​		点击 Registry...
​			勾选compiler.automake.allow.when.app.running
![idea-setting-2.jpg](https://i.loli.net/2019/08/12/OZJFP4kBxvihW97.png)
------
### 多环境配置

不同环境使用不同配置：例如数据库配置，在开发的时候，我们一般用开发数据库，而在生产环境的时候，我们是用正式的数据

### 多环境配置开发

创建多环境配置文件，示例如下：
spring boot允许通过命名约定按照一定的格式(application-{profile}.properties)来定义多个配置文件。
创建多环境配置文件时一定要遵循规范。

新建application-dev.yml

```
test:
  url: dev.com
```

新建application-test.yml

```
test:
  url: test.com
```

修改application.yml

```
spring:
  # springboot多环境配置，指定读取的配置文件，主要名字要与配置文件名字吻合
  profiles:
    active: test
# 如果上边不指定读取的文件，则系统会读取application.yml里边对应的配置
test:
  url: localhost
```

读取配置，验证是否成功：

```
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class DemoController {
    /**
     * 获取配置信息
     */
    @Value("${test.url}")
    private String url;

    /**
     * 读取springboot多环境配置信息
     * @return
     */
    @RequestMapping("many_env")
    public Object manyEnv(){
        return url;
    }
}
```
------
### 项目地址

https://github.com/zhengwx1991/spring-boot-demo.git

### SpringBoot整合Mybatis

###### 引入依赖

```xml
<!--SpringBoot整合Mybatis开始-->
<!-- 引入starter-->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.3.2</version>
</dependency>

<!-- MySQL的JDBC驱动包	-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- 引入第三方数据源 -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.1.6</version>
</dependency>
<!--SpringBoot整合Mybatis结束-->
```

###### 配置数据库连接

```yaml
spring:
  # 配置mysql数据库
  datasource:
    url: jdbc:mysql://192.168.56.105:3306/mybatis?useUnicode=true&characterEncoding=utf-8
    username: root
    password: root
    # 默认数据源为（com.zaxxer.hikari.HikariDataSource）
    # 配置第三方数据源，如下阿里巴巴的druid
    type: com.alibaba.druid.pool.DruidDataSource

# 开启控制台打印sql，一般用于本地开发测试部署时应注释掉
mybatis:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

###### 启动类增加mapper扫描

```java
@SpringBootApplication
@MapperScan("com.sysware.mybatis.mapper")
public class SpringBootDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }
}
```

###### 开发mapper

参考语法：http://www.mybatis.org/mybatis-3/zh/java-api.html

###### 相关资料：

http://www.mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/#Configuration

https://github.com/mybatis/spring-boot-starter/tree/master/mybatis-spring-boot-samples

###### 整合问题集合：

https://my.oschina.net/hxflar1314520/blog/1800035

https://blog.csdn.net/tingxuetage/article/details/80179772

### 事务、隔离级别、传播行为

###### 常见的隔离级别

Serializable： 最严格，串行处理，消耗资源大
Repeatable Read：保证了一个事务不会修改已经由另一个事务读取但未提交（回滚）的数据
Read Committed：大多数主流数据库的默认事务等级
Read Uncommitted：保证了读取过程中不会读取到非法数据

###### 常见的传播行为

PROPAGATION_REQUIRED：支持当前事务，如果当前没有事务，就新建一个事务,最常见的选择
PROPAGATION_SUPPORTS：支持当前事务，如果当前没有事务，就以非事务方式执行
PROPAGATION_MANDATORY：支持当前事务，如果当前没有事务，就抛出异常
PROPAGATION_REQUIRES_NEW：新建事务，如果当前存在事务，把当前事务挂起, 两个事务之间没有关系，一个异常，一个提交，不会同时回滚
PROPAGATION_NOT_SUPPORTED：以非事务方式执行操作，如果当前存在事务，就把当前事务挂起
PROPAGATION_NEVER：以非事务方式执行，如果当前存在事务，则抛出异常

###### Mybatis引入事务

service的实现类引入事务 ，可用作整个类也可以用于指定的的方法@Transantional(propagation=Propagation.REQUIRED)

```java
@Override
@Transactional(propagation = Propagation.REQUIRED)
public int testTransaction() {
    User user = new User();
    user.setAge(9);
    user.setCreateTime(new Date());
    user.setName("事务测试");
    user.setPhone("000121212");
    userMapper.insert(user);
    // 加入异常
    int a = 1/0;
    return user.getId();
}
```