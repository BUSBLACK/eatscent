package com.example.eatscent.service;

import com.example.eatscent.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface LoginService {
    List<User> login(User user);
    User selectById(int id);
    String insertById (User user);

    /**
     * 测试AOP
     * @param id
     * @return
     */
    String aopTest(int id);
    void exidPassword(HashMap map);
    void deleteById(int id);
}
