package com.sysware.mybatis.service;

import com.sysware.mybatis.domain.User;

import java.util.List;

public interface UserService {

    /**
     * 新增
     * @param user
     * @return
     */
    int add(User user);

    /**
     * 查找全部
     * @return
     */
    List<User> listAll();

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    User getById(Long id);

    /**
     * 更新
     * @param user
     */
    void update(User user);

    /**
     * 删除
     * @param userId
     */
    void delete(Long userId);
}
