package com.yjb.goods.biz.impl;

import com.yjb.goods.biz.UserService;
import com.yjb.goods.dao.UserMapper;
import com.yjb.goods.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User findUserByPasswordAndUser(User user) {
        User userByUsernameAndPassword = userMapper.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        return userByUsernameAndPassword;
    }

    @Override
    public User addOrUpdateUser(User user) {
        User saveUser = userMapper.save(user);
        return saveUser;
    }

    @Override
    public User findUserByName(User user) {
        User userByUsername = userMapper.findUserByUsername(user.getUsername());
        return userByUsername;
    }
}
