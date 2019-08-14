package com.sysware.task.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @program: AsyncTask
 * @description: 异步任务类
 * @author: zhengwx
 * @create: 2019-08-14 12:16
 * @version: 1.0.0
 **/
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
