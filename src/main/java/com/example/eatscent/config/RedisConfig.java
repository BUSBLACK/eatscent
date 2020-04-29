package com.example.eatscent.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class RedisConfig {

    Logger logger = LoggerFactory.getLogger(RedisConfig.class);
    @Autowired
    private LettuceConnectionFactory factory;

    @Bean
    public RedisTemplate redisTemplate(){
        factory.setShareNativeConnection(false);//关闭共享连接，重新定义RedisTemplateBean，方便切库
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }
    /**
     * 切换数据库
     * @return
     */
    public RedisTemplate redisTemplateKey(int key){
        factory.setShareNativeConnection(false);//关闭共享连接，重新定义RedisTemplateBean，方便切库
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate().getConnectionFactory().getConnection().select(key);
        return redisTemplate;
    }
}
