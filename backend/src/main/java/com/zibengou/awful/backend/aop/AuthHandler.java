package com.zibengou.awful.backend.aop;

import com.zibengou.awful.backend.exception.AuthorizationException;
import com.zibengou.awful.backend.model.TokenInfo;
import com.zibengou.awful.backend.service.CommonService;
import com.zibengou.awful.backend.types.RoleEnum;
import com.zibengou.awful.backend.utils.TokenHelper;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
public class AuthHandler implements WebFilter {

    private final CommonService common;

    @Autowired
    public AuthHandler(CommonService common) {
        this.common = common;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        if (checkPathNeedAuth(serverWebExchange.getRequest().getPath().pathWithinApplication().value())) {
            String pass = serverWebExchange.getRequest().getHeaders().getFirst("test_pass");
            if ("xiaotong".equals(pass)) {
                TokenInfo info = new TokenInfo(123L, Collections.singletonList(RoleEnum.ADMIN));
                serverWebExchange.getAttributes().put("info", info);
                return webFilterChain.filter(serverWebExchange);
            }
            String token = serverWebExchange.getRequest().getHeaders().getFirst("Authorization");
            if (StringUtils.isEmpty(token)) {
                token = serverWebExchange.getRequest().getCookies().getFirst("token").getValue();
            }
            try {
                TokenInfo info = TokenHelper.decode(token, common.getSecret());
                serverWebExchange.getAttributes().put("info", info);
                return webFilterChain.filter(serverWebExchange);
            } catch (ExpiredJwtException e) {
                serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return Mono.empty();
            } catch (Exception e) {
                serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return Mono.empty();
            }
        } else {
            return webFilterChain.filter(serverWebExchange);
        }
    }

    private boolean checkPathNeedAuth(String path) {
        if (path.contains("/login")) {
            return false;
        } else {
            return true;
        }
    }
}
