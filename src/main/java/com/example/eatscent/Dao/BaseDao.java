package com.example.eatscent.dao;


import org.apache.ibatis.session.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;

/**
 * @author 11397
 */
public class BaseDao implements SqlSessionFactory {
    @Autowired
    private SqlSession sqlSession;

    public boolean doUpdate(String sql){
        int i = sqlSession.update(sql);
        return true;
    }


    @Override
    public SqlSession openSession() {
        return null;
    }

    @Override
    public SqlSession openSession(boolean b) {
        return null;
    }

    @Override
    public SqlSession openSession(Connection connection) {
        return null;
    }

    @Override
    public SqlSession openSession(TransactionIsolationLevel transactionIsolationLevel) {
        return null;
    }

    @Override
    public SqlSession openSession(ExecutorType executorType) {
        return null;
    }

    @Override
    public SqlSession openSession(ExecutorType executorType, boolean b) {
        return null;
    }

    @Override
    public SqlSession openSession(ExecutorType executorType, TransactionIsolationLevel transactionIsolationLevel) {
        return null;
    }

    @Override
    public SqlSession openSession(ExecutorType executorType, Connection connection) {
        return null;
    }

    @Override
    public Configuration getConfiguration() {
        return null;
    }
}
