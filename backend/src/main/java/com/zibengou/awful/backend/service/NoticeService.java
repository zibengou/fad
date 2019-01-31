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
public class NoticeService {

    @Autowired
    private CommonService common;

    @Autowired
    private CacheService cache;

    /**
     * 发布点击事件
     * 修改缓存用户消息数
     * 修改用户通知记录
     *
     * @param clickUserId 发起用户ID
     * @param userName    发起用户名称
     * @param clickId     点击目标ID(评论、消息等)
     * @param clickType   点击类型
     */
    public void click(Long clickUserId, String userName, Long clickId, String clickType) {
        String key = String.join("#", "click", clickId.toString());
    }
}
