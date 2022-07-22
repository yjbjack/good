package com.yjb.goods.dao;

import com.yjb.goods.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMapper extends JpaRepository<User,Integer> {
    User findUserByUsernameAndPassword(String username,String password);
    User findUserByUsername(String username);
}
