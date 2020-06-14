package com.example.eatscent;

import com.example.eatscent.config.RedisConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisConfigTest {
    @Test
    public void redistest(){
        ApplicationContext context = new AnnotationConfigApplicationContext(RedisConfig.class);
        RedisTemplate redisTemplate = (RedisTemplate) context.getBean("redisTemplateA");
        redisTemplate.opsForValue().set("k1","value1");
        redisTemplate.opsForHash().put("k2","value2","value2");
        System.out.println(redisTemplate.opsForValue().get("k1"));
    }
}
