package com.banny.motd.global.util;

import com.banny.motd.domain.user.User;
import com.banny.motd.global.enums.Device;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenUtils {

    /**
     * JWT 토큰 생성
     *
     * @param user 사용자 정보
     * @param key secret key
     * @param expiredTimeMs 만료 시간
     * @return JWT token
     */
    public static String generateJwtToken(User user, Device device, String key, Long expiredTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("userId", user.getId());
        claims.put("loginId", user.getLoginId());
        claims.put("userName", user.getUsername());
        claims.put("email", user.getEmail());
        claims.put("userRole", user.getUserRole().name());
        claims.put("device", device.getName());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
                .signWith(getSigningKey(key), SignatureAlgorithm.HS256)
                .compact();
    }

    public static boolean isTokenValid(String token, String loginId, String key) {
        return getLoginId(token, key).equals(loginId);
    }

    public static String getLoginId(String token, String key) {
        return extractAllClaims(token, key).get("loginId", String.class);
    }

    public static Device getDeviceStr(String token, String key) {
        String deviceStr = extractAllClaims(token, key).get("device", String.class);
        return Device.from(deviceStr);
    }

    public static Claims extractAllClaims(String token, String key) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(key))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static Key getSigningKey(String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
