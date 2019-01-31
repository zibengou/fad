package com.zibengou.awful.backend.controller;

import com.zibengou.awful.backend.dao.domain.Comment;
import com.zibengou.awful.backend.dao.domain.Message;
import com.zibengou.awful.backend.dao.domain.Topic;
import com.zibengou.awful.backend.exception.*;
import com.zibengou.awful.backend.service.BasicService;
import com.zibengou.awful.backend.service.CommonService;
import com.zibengou.awful.backend.service.NoticeService;
import com.zibengou.awful.backend.types.ActionType;
import com.zibengou.awful.backend.types.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;

@RestController
@RequestMapping("/basic")
public class BasicController {

    @Autowired
    private CommonService common;

    @Autowired
    private BasicService service;

    @Autowired
    private NoticeService notice;

    @PostMapping("/topic")
    public Long publishTopic(@RequestBody Topic topic, ServerWebExchange exchange) throws AuthorizationException, ParamInvalidException, DataBaseException {
        Long userId = common.getUserId(exchange);
        return service.publishTopic(userId, topic);
    }

    @PutMapping("/follow/topic/{id}")
    public void followTopic(@PathVariable Long id, ServerWebExchange exchange) throws AuthorizationException, DataBaseException, ResourceDisabledException {
        Long userId = common.getUserId(exchange);
        service.followTopic(id, userId, false);
    }

    @DeleteMapping("/{action}/topic/{id}")
    public void deleteTopicRelation(@PathVariable ActionType action, @PathVariable Long id, ServerWebExchange exchange) throws AuthorizationException, DataBaseException, ResourceDisabledException {
        Long userId = common.getUserId(exchange);
        if (action.equals(ActionType.follow)) {
            service.followTopic(id, userId, true);
        } else {
            throw new ResourceDisabledException("不允许" + action.name() + "操作");
        }
    }

    @PostMapping("/message")
    public Long publishMessage(@RequestBody Message message, @RequestParam(required = false) Long tid, ServerWebExchange exchange) throws AuthorizationException, ParamInvalidException, DataBaseException {
        Long userId = common.getUserId(exchange);
        return service.publishMessage(userId, tid, message);
    }

    @PutMapping("/click/{target}/{id}")
    public void clickAction(@PathVariable ResourceType type, @PathVariable Long id, @RequestParam String clickType, ServerWebExchange exchange) throws AuthorizationException, DataBaseException, ResourceDisabledException {
        Long userId = common.getUserId(exchange);
        if (type.equals(ResourceType.MESSAGE)) {
            String userName = service.clickMessage(id, userId, clickType, false);
            notice.click(userId, userName, id, clickType);
        } else if (type.equals(ResourceType.COMMENT)) {
            String userName = service.clickComment(id, userId, clickType, false);
            notice.click(userId, userName, id, clickType);
        } else {
            throw new ResourceDisabledException("资源" + type.name() + "非法");
        }
    }

    @DeleteMapping("/click/{target}/{id}")
    public void deleteClickAction(@PathVariable ResourceType type, @PathVariable Long id, ServerWebExchange exchange) throws AuthorizationException, DataBaseException, ResourceDisabledException {
        Long userId = common.getUserId(exchange);
        if (type.equals(ResourceType.MESSAGE)) {
            service.clickMessage(id, userId, null, true);
        } else if (type.equals(ResourceType.COMMENT)) {
            service.clickComment(id, userId, null, true);
        } else {
            throw new ResourceDisabledException("资源" + type.name() + "非法");
        }
    }

    @PostMapping("/comment/{id}")
    public Long publishComment(@RequestBody Comment comment, @PathVariable Long id, ServerWebExchange exchange) throws BusinessException {
        Long userId = common.getUserId(exchange);
        return service.publishComment(userId, id, comment);
    }

//    @PutMapping("/{action}/user/{id}")
//    public Long putTopic(@PathVariable ActionType action, @PathVariable String id, ServerWebExchange exchange) throws AuthorizationException, ParamInvalidException, DataBaseException {
//        Long userId = common.getUserId(exchange);
//        return service.publishTopic(userId, topic);
//    }
//
//    @PutMapping("/{action}/message/{id}")
//    public Long putTopic(@PathVariable ActionType action, @PathVariable String id, ServerWebExchange exchange) throws AuthorizationException, ParamInvalidException, DataBaseException {
//        Long userId = common.getUserId(exchange);
//        return service.publishTopic(userId, topic);
//    }
//
//    @PutMapping("/{action}/comment/{id}")
//    public Long putTopic(@PathVariable ActionType action, @PathVariable String id, ServerWebExchange exchange) throws AuthorizationException, ParamInvalidException, DataBaseException {
//        Long userId = common.getUserId(exchange);
//        return service.publishTopic(userId, topic);
//    }

}
