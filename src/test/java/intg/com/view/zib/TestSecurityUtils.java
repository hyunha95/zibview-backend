package com.view.zib;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.HashMap;
import java.util.Map;

public class TestSecurityUtils {

    public static void setJwtAuthentication(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);

        Jwt jwt = new Jwt("tokenValue", null, null, claims, claims);
        JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}