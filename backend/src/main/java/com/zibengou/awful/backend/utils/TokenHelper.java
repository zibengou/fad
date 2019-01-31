package com.zibengou.awful.backend.utils;

import com.zibengou.awful.backend.model.TokenInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class TokenHelper {

    public static String encode(TokenInfo info, Date expire, String secret) {
        Date date = new Date();
        return Jwts.builder()
                .setId(info.getUserId().toString())
                .setSubject(info.getUserId().toString())
                .claim("info", info)
                .setIssuedAt(date)
                .setNotBefore(date)
                .setExpiration(expire)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public static TokenInfo decode(String token, String secret) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        Map<String,Object> res = (Map<String, Object>) claimsJws.getBody().get("info");
        return new TokenInfo(res);
    }
}
