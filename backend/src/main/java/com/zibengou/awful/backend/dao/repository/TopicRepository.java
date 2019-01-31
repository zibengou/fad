package com.zibengou.awful.backend.dao.repository;

import com.zibengou.awful.backend.dao.domain.Topic;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends Neo4jRepository<Topic, Long> {

}
