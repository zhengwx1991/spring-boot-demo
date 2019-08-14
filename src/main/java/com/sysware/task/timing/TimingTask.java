package com.sysware.task.timing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @program: TimingTask
 * @description: 定时任务类
 * @author: zhengwx
 * @create: 2019-08-14 11:20
 * @version: 1.0.0
 **/
@Component
public class TimingTask {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 定时执行任务，2000即为两秒
     */
    @Scheduled(fixedRate = 200000)
    public void start(){
        logger.info("执行了定时任务，时间："+new Date());
    }
}
