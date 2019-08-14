package com.sysware.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: DemoController
 * @description:
 * @author: zhengwx
 * @create: 2019-08-14 11:10
 * @version: 1.0.0
 **/
@RestController
@RequestMapping("v1/test")
public class DemoController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("hello")
    public Object hello(){
        logger.info("这是INFO日志");
        logger.warn("这是warn日志");
        logger.error("这是error日志");
        logger.debug("这是debug日志");
        return "hello";
    }
}
