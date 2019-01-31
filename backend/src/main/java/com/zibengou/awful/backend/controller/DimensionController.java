package com.zibengou.awful.backend.controller;

import com.zibengou.awful.backend.aop.PreAuth;
import com.zibengou.awful.backend.dao.domain.ClickType;
import com.zibengou.awful.backend.dao.repository.ClickTypeRepository;
import com.zibengou.awful.backend.exception.DataBaseException;
import com.zibengou.awful.backend.service.CommonService;
import com.zibengou.awful.backend.types.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.neo4j.util.IterableUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dimension")
public class DimensionController {

    @Autowired
    private CommonService common;

    @PutMapping("/clicktypes")
    @PreAuth(roles = {RoleEnum.ADMIN})
    @CachePut(value = "dimension", key = "'clicktypes'")
    public List<ClickType> publishTopic(@RequestBody(required = false) List<ClickType> clickTypes) throws DataBaseException {
        if (clickTypes == null || clickTypes.size() < 1) {
            List<ClickType> newTypes = new ArrayList<>();
            newTypes.add(new ClickType("smile", "icon-click-smile"));
            newTypes.add(new ClickType("dislike", "icon-click-dislike"));
            newTypes.add(new ClickType("heart", "icon-click-heart"));
            newTypes.add(new ClickType("sad", "icon-click-sad"));
            newTypes.add(new ClickType("applause", "icon-click-applause"));
            newTypes.add(new ClickType("fuck", "icon-click-fuck"));
            common.queryTransaction(session -> {
                session.deleteAll(ClickType.class);
                session.save(newTypes);
            });
            return IterableUtils.toList(newTypes);
        } else {
            common.queryTransaction(session -> {
                session.deleteAll(ClickType.class);
                session.save(clickTypes);
            });
            return IterableUtils.toList(clickTypes);
        }
    }
}
