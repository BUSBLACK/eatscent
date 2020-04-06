package com.example.eatscent.Controller.Impl;

import com.example.eatscent.Controller.LoginController;
import com.example.eatscent.Service.LoginService;
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
    public String Hello(){
        return "index";
    }
    /**
     * 用户登录
     * @param user
     * @return
     */
    @RequestMapping("getlogin/{id}")
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
    public String getLoginById(@PathVariable int id) {
        String str = "6666";
        System.out.println("创建成功");
        return str;
    }

    /**
     * 用户注册
     * @param id
     */
    @RequestMapping("insert/{id}")
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
