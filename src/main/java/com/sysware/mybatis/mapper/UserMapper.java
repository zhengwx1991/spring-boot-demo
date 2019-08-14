package com.sysware.mybatis.mapper;

import com.sysware.mybatis.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 访问数据库接口
 */
public interface UserMapper {

    /**
     * 新增
     * @param user
     * @return
     */
    // 推荐使用#{}取值，不要用${},因为存在注入的风险
    @Insert("INSERT INTO user(name,phone,create_time,age) VALUES(#{name}, #{phone}, #{createTime},#{age})")
    //keyProperty java对象的属性；keyColumn表示数据库的字段
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int insert(User user);

    /**
     * 查找全部
     * @return
     */
    @Select("select * from user")
    @Results({
        @Result(column = "create_time", property = "createTime")
    })
    List<User> listAll();

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @Select("select * from user where id= #{id}")
    @Results({
        @Result(column = "create_time", property = "createTime")
    })
    User getById(Long id);

    /**
     * 更新
     * @param user
     */
    @Update("update user set name=#{name} where id=#{id}")
    void update(User user);

    /**
     * 删除
     * @param userId
     */
    @Delete("delete from user where id=#{uderId}")
    void delete(Long userId);
}
