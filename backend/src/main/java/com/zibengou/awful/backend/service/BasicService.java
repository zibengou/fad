package com.zibengou.awful.backend.service;

import com.zibengou.awful.backend.dao.domain.Comment;
import com.zibengou.awful.backend.dao.domain.Message;
import com.zibengou.awful.backend.dao.domain.Topic;
import com.zibengou.awful.backend.exception.DataBaseException;
import com.zibengou.awful.backend.exception.ParamInvalidException;
import com.zibengou.awful.backend.exception.ResourceDisabledException;
import com.zibengou.awful.backend.exception.ResourceNotExistsException;
import com.zibengou.awful.backend.types.ActionType;
import com.zibengou.awful.backend.types.ResourceType;
import org.neo4j.ogm.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;

@Component
public class BasicService {

    @Autowired
    private CommonService common;

    public Long publishTopic(Long userId, Topic topic) throws ParamInvalidException, DataBaseException {
        checkTopic(topic);
        Long id = common.getId(ResourceType.TOPIC);
        topic.setMid(id);
        common.queryTransaction(session -> {
            session.save(topic);
            String qu = String.format("match(t:Topic{mid:%s}),(u:User{mid:%s}) merge(t)<-[r:" + ActionType.publish.name() + "]-(u) return r", id, userId);
            Result result = session.query(qu, new HashMap<>(1));
            if (result.queryStatistics().getRelationshipsCreated() != 1) {
                throw new DataBaseException(String.format("创建失败 用户ID:%s,关联语法:%s %s", userId, qu, topic));
            }
        });
        return id;
    }

    public void followTopic(Long topicId, Long userId, Boolean delete) throws DataBaseException, ResourceDisabledException {
        String cypher = String.format("match(u:User{mid:%s}),(t:Topic{mid:%s}) merge (u)-[r:%s]->(t) set r.time = %s return r", userId, topicId, ActionType.follow.name(), System.currentTimeMillis());
        if (delete) {
            cypher = String.format("match(u:User{mid:%s}),(t:Topic{mid:%s}) match (u)-[r:%s]->(t) delete r", userId, topicId, ActionType.follow.name());
        }
        if (!common.queryAndCheckUpdate(cypher)) {
            throw new ResourceDisabledException("执行语法:" + cypher);
        }
    }

    public Long publishMessage(Long userId, Long topicId, Message message) throws ParamInvalidException, DataBaseException {
        checkMessage(message);
        Long id = common.getId(ResourceType.MESSAGE);
        message.setMid(id);
        common.queryTransaction(session -> {
            session.save(message);
            String qu = String.format("match(t:Topic{mid:%s}),(m:Message{mid:%s}),(u:User{mid:%s}) merge(t)<-[:BELONG_TO]-(m)<-[r:" + ActionType.publish.name() + "]-(u) return r", topicId, id, userId);
            if (topicId == null) {
                qu = String.format("match(m:Message{mid:%s}),(u:User{mid:%s}) merge(m)<-[r:PUBLISH]-(u) return r", id, userId);
            }
            Result result = session.query(qu, new HashMap<>(1));
            if (result.queryStatistics().getRelationshipsCreated() < 1) {
                throw new DataBaseException(String.format("创建失败 用户ID:%s,关联语法:%s %s", userId, qu, message));
            }
        });
        return id;
    }

    /**
     * 消息点击函数
     *
     * @param messageId 消息ID
     * @param userId    用户ID
     * @param clickType 点击类型
     * @param delete    是否删除
     * @return userName
     * @throws DataBaseException
     * @throws ResourceDisabledException
     */
    public String clickMessage(Long messageId, Long userId, String clickType, Boolean delete) throws DataBaseException, ResourceDisabledException {
        if (delete) {
            String cypher = String.format("match(u:User{mid:%s}),(t:Message{mid:%s}) match (u)-[r:%s]->(t) delete r", userId, messageId, ActionType.click.name());
            if (!common.queryAndCheckUpdate(cypher)) {
                throw new ResourceDisabledException("执行语法:" + cypher);
            }
            return null;
        } else {
            String cypher = String.format("match(u:User{mid:%s}),(t:Message{mid:%s}) merge (u)-[r:%s]->(t) set r.type = %s return u.name", userId, messageId, ActionType.click.name(), clickType);
            return common.query(cypher, String.class);
        }
    }

    /**
     * 回复点击函数
     *
     * @param commentId 回复ID
     * @param userId    用户ID
     * @param clickType 点击类型
     * @param delete    是否删除
     * @return userName
     * @throws DataBaseException
     * @throws ResourceDisabledException
     */
    public String clickComment(Long commentId, Long userId, String clickType, Boolean delete) throws DataBaseException, ResourceDisabledException {
        if (delete) {
            String cypher = String.format("match(u:User{mid:%s}),(t:Comment{mid:%s}) match (u)-[r:%s]->(t) delete r", userId, commentId, ActionType.click.name());
            if (!common.queryAndCheckUpdate(cypher)) {
                throw new ResourceDisabledException("执行语法:" + cypher);
            }
            return null;
        } else {
            String cypher = String.format("match(u:User{mid:%s}),(t:Comment{mid:%s}) merge (u)-[r:%s]->(t) set r.type = %s return u.name", userId, commentId, ActionType.click.name(), clickType);
            return common.query(cypher, String.class);
        }
    }

    public Long publishComment(Long userId, Long pId, Comment comment) throws ParamInvalidException, DataBaseException, ResourceNotExistsException, ResourceDisabledException {
        ResourceType type = ResourceType.getByMid(pId);
        if (!type.equals(ResourceType.MESSAGE) && !type.equals(ResourceType.COMMENT)) {
            throw new ParamInvalidException("ID非法");
        }
        if (type.equals(ResourceType.COMMENT)) {
            String queryUser = String.format("match(c:Comment{mid:%s})<-[:%s]-(u:User) return u.mid", pId, ActionType.publish.name());
            Long uId;
            try {
                uId = common.query(queryUser, Long.class);
            } catch (Exception e) {
                throw new ResourceNotExistsException("评论不存在");
            }
            if (userId.equals(uId)) {
                throw new ResourceDisabledException("不能回复自己的评论");
            }
        }
        checkComment(comment);
        Long id = common.getId(ResourceType.COMMENT);
        comment.setMid(id);
        common.queryTransaction(session -> {
            session.save(comment);
            String qu = String.format("match(c:Comment{mid:%s}),(m{mid:%s}),(u:User{mid:%s}) where m:Message or m:Comment " +
                    "with c,m,u merge(m)<-[:REPLY]-(c)<-[r:%s]-(u) return r", id, pId, userId, ActionType.publish.name());
            Result result = session.query(qu, new HashMap<>(1));
            if (result.queryStatistics().getRelationshipsCreated() < 1) {
                throw new DataBaseException(String.format("创建失败 用户ID:%s,关联语法:%s %s", userId, qu, comment));
            }
        });
        return id;
    }

    private void checkComment(Comment comment) throws ParamInvalidException {
        common.checkName(comment.getName(), false);
        common.checkContent(comment.getContent(), true);
        common.checkUrls(Collections.singletonList(comment.getImg()), "非法IMG");
        common.checkUrls(comment.getLinks(), "非法LINK");
    }

    private void checkMessage(Message message) throws ParamInvalidException {
        common.checkName(message.getName(), false);
        common.checkContent(message.getContent(), true);
        common.checkUrls(message.getImgs(), "非法IMG");
        common.checkUrls(message.getLinks(), "非法LINK");
    }

    private void checkTopic(Topic topic) throws ParamInvalidException {
        common.checkName(topic.getName(), true);
        common.checkContent(topic.getContent(), false);
        common.checkUrls(Collections.singletonList(topic.getImg()), "非法IMG");
        common.checkUrls(topic.getLinks(), "非法LINK");
    }
}
