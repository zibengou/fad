package com.zibengou.awful.backend.dao.repository;

import com.zibengou.awful.backend.dao.domain.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {

    /**
     * 根据mId获取用户
     *
     * @param mid 用户主数据ID
     * @return User
     */
    User findByMid(Long mid);

    User findByNameAndPass(String name, String pass);

    User findByInitId(String initId);

}
