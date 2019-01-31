package com.zibengou.awful.backend.dao.repository;

import com.zibengou.awful.backend.dao.domain.ClickType;
import com.zibengou.awful.backend.dao.domain.Topic;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author stone
 */
@Repository
public interface ClickTypeRepository extends Neo4jRepository<ClickType, Long> {

    @Query("match(c:ClickType) return c.name")
    List<String> findNames();
}
