package com.example.eatscent.dao.mongo;

import com.example.eatscent.mongoBean.Muser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author 11397
 */
@Repository
public interface MuserDao extends MongoRepository<Muser,Long> {
    /**
     * 符合JPA规范命名。不需要实现方法也可用
     * 根据用户名进行模糊查询
     * @param userName
     * @return
     */
    List<Muser> findByuserNameLike(String userName);
}
