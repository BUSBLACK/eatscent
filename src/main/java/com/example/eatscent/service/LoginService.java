package com.example.eatscent.service;

import com.example.eatscent.entity.User;

import java.util.List;

public interface LoginService {
    List<User> login(User user);
    User selectById(int id);
    void insertByPrimaryKey (User user);

    /**
     * 测试AOP
     * @param id
     * @return
     */
    String aopTest(int id);
}
