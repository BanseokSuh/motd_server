package com.lightcc.motd.global.util;

import com.lightcc.motd.domain.user.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtTokenUtils {

    public static String generateToken(User user, String key, Long expiredTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("userId", user.getId());
        claims.put("loginId", user.getLoginId());
        claims.put("userName", user.getUserName());
        claims.put("role", user.getRole().name());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
                .signWith(getSigningKey(key), SignatureAlgorithm.HS256)
                .compact();
    }

    public static Key getSigningKey(String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
