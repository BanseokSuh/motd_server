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


    /**
     * Generate access token
     *
     * @param user
     * @return access token
     */
    public String generateAccessToken(User user) {
        return JwtTokenUtils.generateJwtToken(user, secretKey, accessExpiredTimeMs);
    }

    /**
     * Generate refresh token
     *
     * @param user
     * @return refresh token
     */
    public String generateRefreshToken(User user) {
        return JwtTokenUtils.generateJwtToken(user, secretKey, refreshExpiredTimeMs);
    }

    /**
     * Save refresh token
     *
     * @param userId
     * @param refreshToken
     */
    public void saveRefreshToken(Long userId, String refreshToken) {
        String key = getRefreshTokenKey(userId);
        redisTemplate.opsForValue().set(key, refreshToken, REFRESH_TOKEN_CACHE_TTL);
    }

    /**
     * Get the key for the refresh token
     *
     * @param userId
     * @return
     */
    private String getRefreshTokenKey(Long userId) {
        return "R_TOKEN:" + userId;
    }

    public boolean validateAccessToken(String token) {
        return false;
    }

    public boolean validateRefreshToken(String token) {
        return false;
    }
}
