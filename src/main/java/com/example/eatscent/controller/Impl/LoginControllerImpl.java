package com.example.eatscent.controller.Impl;

import com.example.eatscent.controller.LoginController;
import com.example.eatscent.dao.mongo.MuserDao;
import com.example.eatscent.mongoBean.Muser;
import com.example.eatscent.service.LoginService;
import com.example.eatscent.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
@Controller
@RequestMapping("/login")
public class LoginControllerImpl implements LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginControllerImpl.class);
    @Autowired
    LoginService loginService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    MuserDao muserDao;

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
    @ResponseBody
    public String getLoginById(@PathVariable int id) {
        return loginService.aopTest(id);
    }

    /**
     * 用户注册
     * @param id
     */
    @Override
    @RequestMapping("insertById")
    @ResponseBody
    @CachePut(value = "redisCache",key = "3")
    public String insertById(){
        User user = new User();
        user.setPassword("123456");
        user.setUserName("Black");
        user.setUserComment("3");
        user.setUserGread("2");
        user.setUserIphone("18845121264");
        user.setUserAdress("xiAn");
        return loginService.insertById(user);

    }
    @RequestMapping("up")
    @ResponseBody
    public Map exidPassword(){
        HashMap map = new HashMap();
        map.put("password","66bbb");
        map.put("id","1");
        loginService.exidPassword(map);
        return map;
    }
    @RequestMapping("deleteById")
    public String deleteById(){
        int id = 1;
        loginService.deleteById(id);
        return "Login";
    }
    @RequestMapping("redisTest")
    @ResponseBody
    public boolean redisTset() {
        System.out.println(redisTemplate.opsForValue().get("redisCache1"));
        redisTemplate.opsForValue().set("k3","CLHLKTMXLLYNXYGNLJJ擦擦擦");
        //redis事务的使用
        List list = (List) redisTemplate.execute((RedisOperations operations) -> {
            //设置要监控的key
            operations.watch("k3");
            //开启事务
            operations.multi();
            operations.opsForValue().set("key1", "key1");
            Object ss = operations.opsForValue().get("k1");
            operations.opsForHash().put("key2", "kw2", "kw2");
            //执行命令
            return operations.exec();
        });
        //redis每次只能执行一条命令，很耽误时间，可以采用流水线技术实现批量执行
        Long start = System.currentTimeMillis();
        List list2 = (List) redisTemplate.executePipelined((RedisOperations operations) -> {
           for(int i = 0 ;i < 10000;i++){
               operations.opsForValue().set("kt"+i,"kt"+i);
               String va = String.valueOf(operations.opsForValue().get("kt"+i));
               if(i == 10000){
                   System.out.println("命令进入队列，未执行,值为："+va);
               }
           }
           return null;
        });
        System.out.println("耗时："+(System.currentTimeMillis()-start));
        redisTemplate.opsForValue().increment("k4",56);
        redisTemplate.opsForValue().increment("k5",56);
        redisTemplate.opsForValue().decrement("k5",3);
        redisTemplate.opsForValue().increment("k5",7);
        HashMap hashMap = new HashMap();
        hashMap.put("f1","f");
        hashMap.put("f2","ff");
        redisTemplate.opsForHash().putAll("k6",hashMap);
        redisTemplate.opsForHash().put("k6","f3","fff");
        return true;
    }
    @RequestMapping("mongo")
    @ResponseBody
    public List<Muser> mongoDBTest(Long id){
        Muser muser = new Muser();
        muser.setId(4L);
        muser.setUserName("lzn");
        muser.setNote("YMQ");
        List<String> list = new ArrayList<String>();
        list.add("XN");
        list.add("XT");
        list.add("RQ");
        muser.setRoles(list);
        mongoTemplate.save(muser);
        //return mongoTemplate.findById(id,Muser.class);
//        Criteria criteria = Criteria.where("id").is(1L);
//        Query query = Query.query(criteria);
//        Update update = Update.update("userName","lzn");
//        List<String> list1 = new ArrayList<String>();
//        list1.add("XN");
//        list1.add("XT");
//        list1.add("XJ");
//        update.set("roles",list1);
//        mongoTemplate.updateFirst(query,update,Muser.class);
//        return mongoTemplate.findOne(query,Muser.class);
        return muserDao.findByuserNameLike("n");
    }

}
