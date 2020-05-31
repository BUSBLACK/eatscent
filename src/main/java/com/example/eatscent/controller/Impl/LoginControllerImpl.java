package com.example.eatscent.controller.Impl;

import com.example.eatscent.controller.LoginController;
import com.example.eatscent.service.LoginService;
import com.example.eatscent.until.SendEmail;
import com.example.eatscent.until.info.Email_info;
import com.example.eatscent.config.MongoDBConfig;
import com.example.eatscent.config.RedisConfig;
import com.example.eatscent.entity.User;
import com.mongodb.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@SuppressWarnings("ALL")
@Controller
@RequestMapping("/login")
public class LoginControllerImpl implements LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginControllerImpl.class);
    @Autowired
    LoginService loginService;
    @Autowired
    RedisConfig redisConfig;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    MongoDBConfig mongoDBConfig;
    @Autowired
    MongoClient mongoClient;
    @Autowired
    MongoTemplate mongoTemplate;



    /**
     * 用户登录页面
     * @return
     */
    @RequestMapping("index")
    public String Login(){
        return "Login";
    }
    /**
     * 测试页面
     */
    @RequestMapping("test")
    @Override
    public String Hello(){
        return "Login";
    }
    /**
     * 用户登录
     * @param user
     * @return
     */
    @RequestMapping("getlogin/{id}")
    @Override
    public List<User> getLogin(User user) {
        user.setUserName("Black");
        user.setPassword("123456");
        return loginService.login(user);
    }

    /**
     * 通过UserID查询
     * @param id
     * @return
     */
    @RequestMapping("byId/{id}")
    @Override
    public String getLoginById(@PathVariable int id) {
        return loginService.aopTest(id);
    }

    /**
     * 用户注册
     * @param id
     */
    @RequestMapping("insert/{id}")
    @Override
    public void insertByPrimaryKey(@PathVariable int id){
        User user = new User();
        user.setPassword("123456");
        user.setUserName("Black");
        user.setUserComment("3");
        user.setUserGread("2");
        user.setUserIphone("18845121264");
        user.setUserAdress("xiAn");
        loginService.insertByPrimaryKey(user);
    }
}
