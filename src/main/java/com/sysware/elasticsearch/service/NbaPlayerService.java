package com.sysware.elasticsearch.service;

import com.sysware.elasticsearch.domain.NbaPlayer;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 *
 * @Author: zwx
 * @Company 云迹科技
 * @Date: 2019/10/15 10:25
 */
public interface NbaPlayerService {

    /**
     * 功能描述: 新增球员
     * @param nbaPlayer
     * @return: java.lang.String
     * @auther: zwx
     * @date: 2019/10/15 2:17 下午
     * @throws IOException
     */
    String addNbaPlayer(NbaPlayer nbaPlayer) throws IOException;

    /**
     * 功能描述: 删除球员
     * @param id
     * @return: void
     * @auther: zwx
     * @date: 2019/10/15 2:18 下午
     * @throws IOException
     */
    void removeNbaPlayer(String id) throws IOException;

    /**
     * 功能描述: 删除全部
     * @return: void
     * @throws IOException
     * @auther: zwx
     * @date: 2019/10/15 2:45 下午
     */
    void removeAll() throws IOException;

    /**
     * 功能描述: 更新球员
     * @param nbaPlayer
     * @return: void
     * @auther: zwx
     * @date: 2019/10/15 2:18 下午
     * @throws IOException
     */
    void updateNbaPlayer(NbaPlayer nbaPlayer) throws IOException;

    /**
     * 功能描述: 根据ID查询球员
     * @param id
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @auther: zwx
     * @date: 2019/10/15 2:18 下午
     * @throws IOException
     */
    Map<String, Object> getNbaPlayer(String id) throws IOException;

    /**
     * 功能描述: 从数据库中导入数据到ES
     * @return: void
     * @throws IOException
     * @auther: zwx
     * @date: 2019/10/15 3:28 下午
     */
    void importAll() throws IOException;

    /**
     * 功能描述: match查询（text类型）--根据名字模糊查询
     * @param key
     * @param value
     * @param page
     * @param size
     * @return: java.util.List<com.sysware.elasticsearch.domain.NbaPlayer>
     * @throws IOException
     * @auther: zwx
     * @date: 2019/10/15 4:20 下午
     */
    List<NbaPlayer> searchMatch(String key, String value, int page, int size) throws IOException;

    /**
     * 功能描述: term查询（keyword类型）--根据球队名称，国家查询
     * @param key
     * @param value
     * @param page
     * @param size
     * @return: java.util.List<com.sysware.elasticsearch.domain.NbaPlayer>
     * @throws IOException
     * @auther: zwx
     * @date: 2019/10/15 4:20 下午
     */
    List<NbaPlayer> searchTerm(String key, String value, int page, int size) throws IOException;

    /**
     * 功能描述: match查询--Prefix前缀查询--根据单词首字母模糊查询
     * @param key
     * @param value
     * @param page
     * @param size
     * @return: java.util.List<com.sysware.elasticsearch.domain.NbaPlayer>
     * @throws IOException
     * @auther: zwx
     * @date: 2019/10/15 5:33 下午
     */
    List<NbaPlayer> searchMatchPrefix(String key, String value, int page, int size) throws IOException;

}
