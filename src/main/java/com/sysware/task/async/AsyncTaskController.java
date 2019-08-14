package com.sysware.task.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

/**
 * @program: AsyncTaskController
 * @description: 异步任务controller
 * @author: zhengwx
 * @create: 2019-08-14 12:44
 * @version: 1.0.0
 **/
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
