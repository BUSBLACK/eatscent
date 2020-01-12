package com.example.eatscent.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableTransactionManagement
public class MongoDBConfig {
    Logger logger = LoggerFactory.getLogger(MongoDBConfig.class);
    @Value("${spring.mongodb.host}")
    private String host;
    @Value("${spring.mongodb.port}")
    private int port;
    @Value("${spring.mongodb.username}")
    private String username;
    @Value("${spring.mongodb.password}")
    private String password;
    @Value("${spring.mongodb.databaseName}")
    private String databaseName;
    /**
     * 返回MongoDB连接
     * @return
     */
    @Bean
    public MongoClient mongoClient(){
        MongoClient mongoClient = new MongoClient();
        try {
            //连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址
            //ServerAddress()两个参数分别为 服务器地址 和 端口
            ServerAddress serverAddress = new ServerAddress(host,port);
            List<ServerAddress> addrs = new ArrayList<ServerAddress>();
            addrs.add(serverAddress);
            //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
            MongoCredential credential = MongoCredential.createScramSha1Credential(username, databaseName, password.toCharArray());
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();
            credentials.add(credential);
            //通过连接认证获取MongoDB连接
             mongoClient = new MongoClient(addrs,credentials);

        } catch (Exception e) {
            logger.error("MongoDBConfigClent-fail"+e.getClass().getName() + ": " + e.getMessage());
        }
        return mongoClient;
    }

    /**
     * 创建mongoDB的集合
     * @param str1
     */
    public void mongoDBCreatCollection(String str1){
        try {
            MongoDatabase mongoDatabase = mongoClient().getDatabase(databaseName);
            mongoDatabase.createCollection(str1);
        }catch (Exception e) {
            logger.error("mongoDBCreatCollection-fail"+e.getMessage());
        }
    }
}
