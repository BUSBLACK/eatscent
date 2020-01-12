package com.example.eatscent.Dao;

import com.example.eatscent.entity.User;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    /**
     * 用户登录
     * @param user
     * @return
     */
    List<User> selectByLogin (User user);
    User selectByLoginID (int id);

    /**
     * 用户注册
     * @param user
     */
    void insertByPrimaryKey (User user);

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
    User selectID (int id);

}
