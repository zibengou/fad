package com.zibengou.awful.backend.service;

import com.zibengou.awful.backend.dao.domain.ClickType;
import com.zibengou.awful.backend.dao.repository.ClickTypeRepository;
import com.zibengou.awful.backend.exception.DataBaseException;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.neo4j.util.IterableUtils;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Component
public class CacheService {

    private final JedisPool pool;

    @Value("${token.secret: stone2019}")
    private String secret;

    private final SessionFactory factory;

    private final ClickTypeRepository clickTypeRepository;

    @Autowired
    public CacheService(JedisPool pool, SessionFactory factory, ClickTypeRepository clickTypeRepository) {
        this.pool = pool;
        this.factory = factory;
        this.clickTypeRepository = clickTypeRepository;
    }

    @Cacheable(value = "dimension", key = "'clicktype_names'", sync = true)
    public List<String> getClickTypeNames() {
        return clickTypeRepository.findNames();
    }

    public Long incr(String key) throws DataBaseException {
        return exec(jedis -> jedis.incr(key));
    }

    private <T> T exec(Runnable runnable) throws DataBaseException {
        try (Jedis jedis = this.pool.getResource()) {
            return (T) runnable.run(jedis);
        } catch (Exception e) {
            throw new DataBaseException("缓存服务异常:" + e.getMessage());
        }
    }

    interface Runnable {
        Object run(Jedis jedis);
    }
}
