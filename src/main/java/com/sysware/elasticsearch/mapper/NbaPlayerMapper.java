package com.sysware.elasticsearch.mapper;

import com.sysware.elasticsearch.domain.NbaPlayer;
import com.sysware.mybatis.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 描述：
 *
 * @Author: zwx
 * @Company 云迹科技
 * @Date: 2019/10/15 09:26
 */
@Mapper
public interface NbaPlayerMapper {

    /**
     * 功能描述: 查询全部球员
     * @return: java.util.List<com.sysware.elasticsearch.domain.NbaPlayer>
     * @auther: zwx
     * @date: 2019/10/15 9:28 上午
     */
    @Select("select * from nba_player")
    public List<NbaPlayer> selectAll();
}
