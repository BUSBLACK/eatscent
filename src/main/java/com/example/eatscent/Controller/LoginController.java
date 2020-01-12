package com.example.eatscent.Controller;

import com.example.eatscent.entity.User;

import java.util.List;

public interface LoginController {
    List<User> getLogin(User user);
    String getLoginById(int id);
    void insertByPrimaryKey (int user);
}
