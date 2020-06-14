package com.example.eatscent.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.time.Duration;

/**
 * @author 11397
 */
@Configuration
@EnableTransactionManagement
public class RedisConfig {
    private RedisConnectionFactory connectionFactory = null;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * IP
     */
    @Value("$(spring.redis.host)")
    private String hostName;
    /**
     * 端口
     */
    @Value("$(spring.redis.port)")
    private String port;
    /**
     * 密码
     */
    @Value("$(spring.redis.password)")
    private String passWord;



    /**
     * 通过RedisConnectionFactory接口来配置redis连接池
     * 通过RedisConnectionFactory来创建RedisConnection接口对象
     * @return
     */
    public RedisConnectionFactory initRedisConnectionFactory(){
        if(this.connectionFactory != null){
            return this.connectionFactory;
        }
        //redis连接池
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        //最大空闲连接数
        poolConfig.setMaxIdle(20);
        //最大连接数
        poolConfig.setMaxTotal(40);
        //最大等待毫秒数
        poolConfig.setMaxWaitMillis(5000);
        //创建jedis连接工厂
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(poolConfig);
        //获取单机redis配置
        RedisStandaloneConfiguration redisStandaloneConfiguration = connectionFactory.getStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName("127.0.0.1");
        redisStandaloneConfiguration.setPort(6379);
        redisStandaloneConfiguration.setPassword("123456");
        this.connectionFactory = connectionFactory;
        return connectionFactory;
    }

    /**
     * RedisTemplate对象自动从RedisConnectionFactory获取一条连接
     * RedisTemplate执行完对应后的命令后会自动关闭连接
     * @return
     */
    @Bean(name = "redisTemplateA")
    public RedisTemplate initRedisTemplateA(){
        RedisTemplate redisTemplate = new RedisTemplate();
        //获取RedisTemplate的StringSerializer序列器
        //redis是一种基于字符串存储的NoSQL,Java是基于对象的语言，对象是无法存储到redis中的
        //Java提供了序列化机制，实现Serializable接口，通过将类对象进行序列化就可以存储到redis中，Java也可以反序列化得到对象
        //Redis中默认的序列化器是JdkSerializableRedisSerializer，为了方便，将序列化器设置成字符串序列化器StringSerializer
        RedisSerializer stringSerializerA = redisTemplate.getStringSerializer();
        redisTemplate.setKeySerializer(stringSerializerA);
        redisTemplate.setValueSerializer(stringSerializerA);
        redisTemplate.setHashKeySerializer(stringSerializerA);
        redisTemplate.setHashValueSerializer(stringSerializerA);
        redisTemplate.setConnectionFactory(initRedisConnectionFactory());
        return redisTemplate;
    }
    /**
     * 定义自定义后的初始化方法
     */
    @PostConstruct
    public void initTemplate(){
        RedisSerializer stringSerializer = redisTemplate.getStringSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
    }

    /**
     * 自定义Redis缓存管理器
     * @return
     */
    @Bean(name = "redisCacheManager")
    public RedisCacheManager initRedisCacheManager(){
        //Redis加入写锁
        RedisCacheWriter writer = RedisCacheWriter.lockingRedisCacheWriter(connectionFactory);
        //Redis缓存器默认设置
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        //设置序列化器
        cacheConfiguration = cacheConfiguration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer( new JdkSerializationRedisSerializer()));
        //设置前缀不可用
        cacheConfiguration = cacheConfiguration.disableKeyPrefix();
        //设置1000ms不超时
        cacheConfiguration = cacheConfiguration.entryTtl(Duration.ofDays(1000));
        //创建Redis缓存管理器
        RedisCacheManager redisCacheManager = new RedisCacheManager(writer,cacheConfiguration);
        return redisCacheManager;
    }

}
