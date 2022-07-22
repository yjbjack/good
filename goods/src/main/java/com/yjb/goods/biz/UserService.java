package com.yjb.goods.biz;

import com.yjb.goods.pojo.User;

public interface UserService {
    User findUserByPasswordAndUser(User user);
    User findUserByName(User user);
    User addOrUpdateUser(User user);
}
