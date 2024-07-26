package com.banny.motd.domain.user.application.repository;

import com.banny.motd.domain.user.domain.User;
import com.banny.motd.global.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class UserTokenManager {

    private final RedisTemplate<String, String> redisTemplate;
    private final static Duration REFRESH_TOKEN_CACHE_TTL = Duration.ofDays(1); // 1 day

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.access-expired-time-ms}")
    private Long accessExpiredTimeMs;

    @Value("${jwt.token.refresh-expired-time-ms}")
    private Long refreshExpiredTimeMs;


    public String generateAccessToken(User user) {
        return JwtTokenUtils.generateJwtToken(user, secretKey, accessExpiredTimeMs);
    }

    public String generateRefreshToken(User user) {
        return JwtTokenUtils.generateJwtToken(user, secretKey, refreshExpiredTimeMs);
    }

    public void saveRefreshToken(Long userId, String refreshToken) {
        String key = getKey(userId);
        redisTemplate.opsForValue().set(key, refreshToken, REFRESH_TOKEN_CACHE_TTL);
    }

    private String getKey(Long userId) {
        return "token:" + userId.toString();
    }

    public boolean validateAccessToken(String token) {
        return false;
    }

    public boolean validateRefreshToken(String token) {
        return false;
    }
}
