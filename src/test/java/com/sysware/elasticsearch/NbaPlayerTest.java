package com.sysware.elasticsearch;

import com.alibaba.fastjson.JSONObject;
import com.sysware.elasticsearch.domain.NbaPlayer;
import com.sysware.elasticsearch.mapper.NbaPlayerMapper;
import com.sysware.elasticsearch.service.NbaPlayerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 *
 * @Author: zwx
 * @Company 云迹科技
 * @Date: 2019/10/15 11:16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NbaPlayerTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private NbaPlayerService service;

    @Resource
    private NbaPlayerMapper mapper;

    @Test
    public void selectAll(){
        List<NbaPlayer> all = mapper.selectAll();
        logger.info(JSONObject.toJSONString(all));
    }

    @Test
    public void addNbaPlayer() throws IOException{
        NbaPlayer nbaPlayer = new NbaPlayer();
        nbaPlayer.setId(888);
        nbaPlayer.setDisplayName("杨超越111");
        service.addNbaPlayer(nbaPlayer);
    }

    @Test
    public void removeNbaPlayer() throws IOException{

    }

    @Test
    public void updateNbaPlayer() throws IOException{

    }

    @Test
    public void getNbaPlayer() throws IOException{

    }
}
