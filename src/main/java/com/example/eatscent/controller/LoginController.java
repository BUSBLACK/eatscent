package com.example.eatscent.controller;

import com.example.eatscent.entity.User;

import java.util.List;

@SuppressWarnings("ALL")
public interface LoginController {
    List<User> getLogin(User user);
    String getLoginById(int id);
    void insertByPrimaryKey (int user);
    public String Hello();
}
