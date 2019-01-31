package com.zibengou.awful.backend.controller;

import com.zibengou.awful.backend.dao.domain.User;
import com.zibengou.awful.backend.dao.repository.UserRepository;
import com.zibengou.awful.backend.exception.AuthorizationException;
import com.zibengou.awful.backend.exception.ParamInvalidException;
import com.zibengou.awful.backend.exception.ResourceNotExistsException;
import com.zibengou.awful.backend.model.TokenInfo;
import com.zibengou.awful.backend.model.TokenResponse;
import com.zibengou.awful.backend.service.CommonService;
import com.zibengou.awful.backend.utils.SHA256Helper;
import com.zibengou.awful.backend.utils.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommonService common;

    @Value("${token.access.expire: 1}")
    private Integer accessExpireHour;

    @Value("${token.refresh.expire: 240}")
    private Integer refreshExpireHour;

    @PostMapping("/login")
    public TokenResponse login(@RequestBody Map<String, String> request, ServerWebExchange exchange) throws ParamInvalidException, ResourceNotExistsException {
        User user;
        if (request.containsKey("initId")) {
            user = userRepository.findByInitId(request.get("initId"));
        } else if (request.containsKey("user") && request.containsKey("pass")) {
            String pass = request.get("pass");
            user = userRepository.findByNameAndPass(request.get("user"), SHA256Helper.encode(pass));
        } else {
            throw new ParamInvalidException("参数错误");
        }
        if (user == null) {
            throw new ResourceNotExistsException("用户名或密码错误");
        } else {
            Long time = System.currentTimeMillis();
            TokenInfo tokenInfo = new TokenInfo(user.getMid(), user.getRoles());
            String accessToken = TokenHelper.encode(tokenInfo, new Date(time + 3600000 * accessExpireHour), common.getSecret());
            String refreshToken = TokenHelper.encode(tokenInfo, new Date(time + 3600000 * refreshExpireHour), common.getSecret());
            ResponseCookie cookie = ResponseCookie.from("token", accessToken).httpOnly(false).secure(false).build();
            exchange.getResponse().addCookie(cookie);
            return new TokenResponse(accessToken, refreshToken);
        }
    }

    @GetMapping("/refresh")
    public TokenResponse refresh(@RequestParam("refreshToken") String rt, ServerWebExchange exchange) throws AuthorizationException {
        Long time = System.currentTimeMillis();
        try {
            TokenInfo info = TokenHelper.decode(rt, common.getSecret());
            User user = userRepository.findByMid(info.getUserId());
            TokenInfo tokenInfo = new TokenInfo(user.getMid(), user.getRoles());
            String accessToken = TokenHelper.encode(tokenInfo, new Date(time + 3600000 * accessExpireHour), common.getSecret());
            String refreshToken = TokenHelper.encode(tokenInfo, new Date(time + 3600000 * refreshExpireHour), common.getSecret());
            ResponseCookie cookie = ResponseCookie.from("token", accessToken).httpOnly(false).secure(false).build();
            exchange.getResponse().addCookie(cookie);
            return new TokenResponse(accessToken, refreshToken);
        }catch (Exception e){
            throw new AuthorizationException("令牌过期");
        }
    }
}
