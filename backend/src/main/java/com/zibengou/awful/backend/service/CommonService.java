package com.zibengou.awful.backend.service;

import com.zibengou.awful.backend.exception.AuthorizationException;
import com.zibengou.awful.backend.exception.DataBaseException;
import com.zibengou.awful.backend.exception.ParamInvalidException;
import com.zibengou.awful.backend.model.TokenInfo;
import com.zibengou.awful.backend.types.ResourceType;
import com.zibengou.awful.backend.utils.CommonParams;
import com.zibengou.awful.backend.utils.Constant;
import com.zibengou.awful.backend.utils.NetUtil;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.ogm.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommonService {

    private final JedisPool pool;

    private static final String INCR_KEY = "mdmId";

    @Value("${token.secret: stone2019}")
    private String secret;

    private final SessionFactory factory;

    private final CacheService cache;

    @Autowired
    public CommonService(JedisPool pool, SessionFactory factory, CacheService cache) {
        this.pool = pool;
        this.factory = factory;
        this.cache = cache;
    }

    /**
     * 主数据ID生成函数
     * 格式：{YYMMDD}{INCR} 日期+自增量
     * REDIS提供自增量，并且每天更新
     *
     * @param type 资源类型
     * @return 资源ID
     */
    public synchronized long getId(ResourceType type) throws DataBaseException {
        LocalDate today = LocalDate.now();
        Long redisId = cache.incr(INCR_KEY);
        String id = type.getType() + today.format(DateTimeFormatter.ofPattern("YYMMDD")) + redisId;
        return Long.parseLong(id);
    }

    /**
     * 根据exchange获取Token信息
     *
     * @param exchange webflux context
     * @return TokenInfo
     * @throws AuthorizationException 权限异常
     */
    public TokenInfo getInfo(ServerWebExchange exchange) throws AuthorizationException {
        try {
            return exchange.getAttribute("info");
        } catch (Exception e) {
            throw new AuthorizationException("token信息错误");
        }
    }

    /**
     * 根据exchange获取UserId
     *
     * @param exchange webflux context
     * @return userId
     * @throws AuthorizationException 权限异常
     */
    public Long getUserId(ServerWebExchange exchange) throws AuthorizationException {
        return getInfo(exchange).getUserId();
    }

    public String getSecret() {
        return secret;
    }

    public Result query(String cypher) {
        return query(cypher, new HashMap<>());
    }

    public Result query(String cypher, Map<String, Object> params) {
        return getSession().query(cypher, params);
    }

    public <T> T query(String cypher, Class<T> cls) {
        return query(cypher, new HashMap<>(1), cls);
    }

    public void queryTransaction(List<String> cyphers) throws DataBaseException {
        queryTransaction(s -> cyphers.forEach(c -> s.query(c, new HashMap<>())));
    }

    public void queryTransaction(Runnable runnable) throws DataBaseException {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            runnable.run(session);
            tx.commit();
        } catch (DataBaseException e) {
            tx.rollback();
            throw e;
        } catch (Exception e) {
            tx.rollback();
            throw new DataBaseException(e.getMessage());
        } finally {
            tx.close();
        }
    }

    public boolean queryAndCheckUpdate(String cypher) throws DataBaseException {
        return queryAndCheckUpdate(cypher, new HashMap<>(1));
    }

    public boolean queryAndCheckUpdate(String cypher, Map<String, Object> params) throws DataBaseException {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            Result result = session.query(cypher, params);
            if (!result.queryStatistics().containsUpdates()) {
                tx.rollback();
                return false;
            }
            tx.commit();
            return true;
        } catch (Exception e) {
            tx.rollback();
            throw new DataBaseException(e.getMessage());
        } finally {
            tx.close();
        }
    }

    public <T> T query(String cypher, Map<String, Object> params, Class<T> cls) {
        return getSession().queryForObject(cls, cypher, params);
    }

    private Session getSession() {
        return this.factory.openSession();
    }

    public void checkName(String name, Boolean notNull) throws ParamInvalidException {
        if (StringUtils.isEmpty(name)) {
            if (notNull) {
                throw new ParamInvalidException("缺少name");
            }
        } else if (name.length() > CommonParams.NAME_MAX_LENGTH) {
            throw new ParamInvalidException("name长度不能超过" + CommonParams.NAME_MAX_LENGTH);
        } else if (name.length() < CommonParams.TOPIC_NAME_MIN_LENGTH) {
            throw new ParamInvalidException("name长度不能少于" + CommonParams.TOPIC_NAME_MIN_LENGTH);
        }
        checkSensitive(name);
    }

    public void checkContent(String content, Boolean notNull) throws ParamInvalidException {
        if (StringUtils.isEmpty(content)) {
            if (notNull) {
                throw new ParamInvalidException("缺少content");
            }
        } else if (content.length() > CommonParams.CONTENT_MAX_LENGTH) {
            throw new ParamInvalidException("content长度不能超过" + CommonParams.CONTENT_MAX_LENGTH);
        } else if (content.length() < CommonParams.CONTENT_MIN_LENGTH) {
            throw new ParamInvalidException("content长度不能少于" + CommonParams.CONTENT_MIN_LENGTH);
        }
        checkSensitive(content);
    }

    public void checkUrls(List<String> urls, String errPre) throws ParamInvalidException {
        String errUrl = NetUtil.checkUrls(urls);
        if (errUrl != null) {
            throw new ParamInvalidException(errPre + ":" + errUrl);
        }
    }

    /**
     * 校验敏感词
     *
     * @param str 检验字符串
     * @throws ParamInvalidException 参数非法异常
     */
    public void checkSensitive(String str) throws ParamInvalidException {
        String[] sensitives = Constant.filterSensitive(str);
        if (sensitives.length > 0) {
            throw new ParamInvalidException("敏感词:" + String.join(",", sensitives));
        }
    }

    public void checkClickType(String clickType) throws ParamInvalidException {
        List<String> clickTypes = cache.getClickTypeNames();
        if (!clickTypes.contains(clickType)) {
            throw new ParamInvalidException("点击类型不存在");
        }
    }

    /**
     * Neo4j执行事务回调类
     */
    public interface Runnable {
        void run(Session session) throws Exception;
    }

}
