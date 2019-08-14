package com.sysware.mybatis.service;

import com.sysware.mybatis.domain.User;
import com.sysware.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: UserServiceImpl
 * @description: 用户service
 * @author: zhengwx
 * @create: 2019-08-14 16:38
 * @version: 1.0.0
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int add(User user) {
        userMapper.insert(user);
        return user.getId();
    }

    @Override
    public List<User> listAll() {
        return userMapper.listAll();
    }

    @Override
    public User getById(Long id) {
        return userMapper.getById(id);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public void delete(Long userId) {
        userMapper.delete(userId);
    }
}
