package com.example.eatscent.Service.Impl;

import com.example.eatscent.Dao.UserMapper;
import com.example.eatscent.Service.LoginService;
import com.example.eatscent.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    UserMapper userMapper;

    /**
     * 用户登录
     * @param user
     * @return
     */
    @Override
    public List<User> login(User user) {
        List<User> loginList = userMapper.selectByLogin(user);
        return loginList;
    }

    @Override
    public User selectById(int id) {
        return userMapper.selectByLoginID(id);
    }

    /**
     * 用户注册
     * @param user
     */
    @Override
    public void insertByPrimaryKey(User user) {
        userMapper.insertByPrimaryKey(user);
    }
}
