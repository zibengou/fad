package com.zibengou.awful.backend.model;

import com.zibengou.awful.backend.types.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenInfo {
    private Long userId;
    private List<RoleEnum> roles;

    public TokenInfo(Map<String, Object> token) {
        if (token.containsKey("userId")) {
            this.userId = Long.parseLong(token.get("userId").toString());
        }
        if (token.containsKey("roles")) {
            this.roles = ((List<String>) token.getOrDefault("roles", new ArrayList<>())).stream().map(r -> RoleEnum.valueOf(r.toString())).collect(Collectors.toList());
        }
    }
}

