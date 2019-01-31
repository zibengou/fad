package com.zibengou.awful.backend.controller;

import com.zibengou.awful.backend.dao.domain.Topic;
import com.zibengou.awful.backend.exception.AuthorizationException;
import com.zibengou.awful.backend.exception.DataBaseException;
import com.zibengou.awful.backend.exception.ParamInvalidException;
import com.zibengou.awful.backend.model.RecommendInfo;
import com.zibengou.awful.backend.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@RestController
@RequestMapping("/batch")
public class BatchController {

    @Autowired
    private CommonService common;

    @GetMapping("/recommend")
    public List<RecommendInfo> publishTopic(@RequestBody Topic topic, ServerWebExchange exchange) throws AuthorizationException, ParamInvalidException, DataBaseException {
        Long userId = common.getUserId(exchange);
        return service.publishTopic(userId, topic);
    }

}
