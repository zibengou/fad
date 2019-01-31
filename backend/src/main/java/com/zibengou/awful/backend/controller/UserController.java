package com.zibengou.awful.backend.controller;

import com.zibengou.awful.backend.aop.PreAuth;
import com.zibengou.awful.backend.dao.domain.User;
import com.zibengou.awful.backend.dao.repository.UserRepository;
import com.zibengou.awful.backend.exception.DataBaseException;
import com.zibengou.awful.backend.exception.ParamInvalidException;
import com.zibengou.awful.backend.exception.ResourceNotExistsException;
import com.zibengou.awful.backend.model.PageableResponse;
import com.zibengou.awful.backend.service.CommonService;
import com.zibengou.awful.backend.types.ResourceType;
import com.zibengou.awful.backend.types.RoleEnum;
import com.zibengou.awful.backend.utils.Constant;
import com.zibengou.awful.backend.utils.SHA256Helper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.*;

@RestController
@RequestMapping("/basic")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommonService commonService;

    @PreAuth(match = "(#info.userId.equals(#id) && #info.roles.contains('NORMAL')) || #info.roles.contains('ADMIN') ")
    @GetMapping("/user/{id}")
    public Mono<User> getUserById(@PathVariable Long id, ServerWebExchange exchange) throws ResourceNotExistsException {
        return getUserById(id);
    }

    @GetMapping("/user")
    @PreAuth(roles = {RoleEnum.ADMIN, RoleEnum.FATHER})
    public Mono<PageableResponse<User>> getUserList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size,ServerWebExchange exchange) throws ResourceNotExistsException {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("name"));
        Page<User> userPage = userRepository.findAll(pageable);
        PageableResponse<User> response = new PageableResponse<>(userPage.getTotalElements(), page, size, userPage.getContent());
        return Mono.just(response);
    }

    @PostMapping("/user")
    @PreAuth(roles = {RoleEnum.ADMIN})
    public Mono<User> createUser(@RequestBody User user,ServerWebExchange exchange) throws ParamInvalidException, DataBaseException {
        Long mid = commonService.getId(ResourceType.USER);
        user.setMid(mid);
        checkParams(user.getMid().toString(), user.getInitId(), user.getName());
        if (StringUtils.isNotEmpty(user.getPass())) {
            user.setPass(SHA256Helper.encode(user.getPass()));
        }
        user.setRoles(Collections.singletonList(RoleEnum.NORMAL));
        return Mono.just(userRepository.save(user));
    }

    @PutMapping("/user/{id}")
    @PreAuth(roles = {RoleEnum.ADMIN})
    public Mono<User> updateUser(@PathVariable Long id, @RequestBody User user,ServerWebExchange exchange) throws ParamInvalidException, ResourceNotExistsException {
        User u = getUserById(id).block();
        user.setId(u.getId());
        user.setMid(u.getMid());
        return Mono.just(userRepository.save(user));
    }

    @PatchMapping("/user/{id}")
    @PreAuth(match = "(#info.userId.equals(#id) && #info.roles.contains('NORMAL')) || #info.roles.contains('ADMIN') ")
    public Mono<User> patchUser(@PathVariable Long id, @RequestBody Map<String, Object> user,ServerWebExchange exchange) throws ParamInvalidException, ResourceNotExistsException {
        User u = getUserById(id).block();
        user.remove("mid");
        user.remove("id");
        user.remove("roles");
        user.remove("createTime");
        user.remove("updateTime");
        if (user.containsKey("pass")) {
            user.put("pass", SHA256Helper.encode(user.get("pass").toString()));
        }
        Constant.merge(u, user);
        return Mono.just(userRepository.save(u));
    }

    @DeleteMapping("/user/{id}")
    @PreAuth(roles = {RoleEnum.ADMIN})
    public Mono<User> updateUser(@PathVariable Long id) throws ResourceNotExistsException {
        User u = getUserById(id).block();
        userRepository.delete(u);
        return Mono.just(u);
    }

    private Mono<User> getUserById(Long id) throws ResourceNotExistsException {
        User user = userRepository.findByMid(id);
        if (user == null) {
            throw new ResourceNotExistsException("用户不存在");
        }
        return Mono.just(user);
    }

    private void checkParams(String... params) throws ParamInvalidException {
        if (StringUtils.isAnyEmpty(params)) {
            throw new ParamInvalidException("参数错误,参数不能为空");
        }
    }
}
