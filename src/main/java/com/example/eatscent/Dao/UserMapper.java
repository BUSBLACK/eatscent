package com.example.eatscent.dao;

import com.example.eatscent.entity.User;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 11397
 */
@SuppressWarnings("ALL")
@Repository
public interface UserMapper  {
    /**
     * 用户登录
     * @param user
     * @return
     */
    List<User> selectByLogin (User user);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    User selectByLoginId (int id);

    /**
     * 用户注册
     * @param user
     */
    @CachePut(value = "redisCache",key = "1")
    int insertById (User user);

    /**
     * 通过id
     * @param id查询
     * @return
     */
    @Select("SELECT * FROM user WHERE UserId = #{id}")
    @Results({
            @Result(property = "UserID",  column = "UserID"),
            @Result(property = "UserName", column = "UserNamr"),
            @Result(property = "Password", column = "Password"),
            @Result(property = "UserIphone", column = "UserIphone"),
            @Result(property = "UserAdress", column = "UserAdress"),
            @Result(property = "UserGread", column = "UserGread"),
            @Result(property = "UserComment", column = "UserComment"),
    })
    User selectId (int id);

    /**
     * 修改密码
     * @param map
     * @return
     */
    void updatePassword(HashMap map);

    /**
     * 通过ID删除
     * @param id
     */
    void deleteById(int id);

}
