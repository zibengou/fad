package com.zibengou.awful.backend.aop;

import com.zibengou.awful.backend.exception.AuthorizationException;
import com.zibengou.awful.backend.model.TokenInfo;
import com.zibengou.awful.backend.types.RoleEnum;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class AuthAspect {

    private static final String EXCHANGE_NAME = "exchange";

    @Pointcut("@annotation(preAuth)")
    public Long serviceAspect(PreAuth preAuth) {
        return System.currentTimeMillis();
    }

    @Before(value = "serviceAspect(preAuth)")
    public void before(JoinPoint joinPoint, PreAuth preAuth) throws AuthorizationException {
        if (preAuth.roles().length > 0 || !"".equals(preAuth.match())) {
            Map<String, Object> params = parseParams(joinPoint);
            if (!checkAuth(params, preAuth)) {
                throw new AuthorizationException("无权访问");
            }
        }
    }

    private boolean checkAnd(Map<String, Object> params, PreAuth[] auths) {
        for (PreAuth preAuth : auths) {
            if (!checkAuth(params, preAuth)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkOr(Map<String, Object> params, PreAuth[] auths) {
        for (PreAuth preAuth : auths) {
            if (checkAuth(params, preAuth)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkAuth(Map<String, Object> params, PreAuth preAuth) {
        if (params.containsKey(EXCHANGE_NAME)) {
            ServerWebExchange exchange = (ServerWebExchange) params.get(EXCHANGE_NAME);
            TokenInfo info = (TokenInfo) exchange.getAttributes().get("info");
            params.put("info", info);
            if (!checkMatch(preAuth.match(), params) || !checkRoles(preAuth.roles(), info.getRoles())) {
                return false;
            }
        }
        return true;
    }

    private boolean checkMatch(String match, Map<String, Object> params) {
        if (StringUtils.isEmpty(match)) {
            return true;
        }
        try {
            return (Boolean) express(match, params);
        } catch (Exception ignore) {
            return false;
        }

    }

    private boolean checkRoles(RoleEnum[] roles, List<RoleEnum> userRoles) {
        if (roles.length > 0) {
            for (RoleEnum role : roles) {
                if (!userRoles.contains(role)) {
                    return false;
                }
            }
        }
        return true;
    }

    private Object express(String exp, Map<String, Object> params) {
        ExpressionParser elParser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariables(params);
        Expression expression = elParser.parseExpression(exp);
        return expression.getValue(context);
    }

    private Map<String, Object> parseParams(JoinPoint joinPoint) {
        Object[] params = joinPoint.getArgs();
        String[] paramNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Map<String, Object> res = new LinkedHashMap<>();
        for (int i = 0; i < paramNames.length; i++) {
            String key = paramNames[i];
            res.put(key, params[i]);
        }
        return res;
    }
}
