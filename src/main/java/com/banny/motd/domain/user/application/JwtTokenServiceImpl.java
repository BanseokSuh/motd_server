package com.banny.motd.domain.user.application;

import com.banny.motd.domain.user.domain.User;
import com.banny.motd.global.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements TokenService {

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.access-expired-time-ms}")
    private Long accessExpiredTimeMs;

    @Value("${jwt.token.refresh-expired-time-ms}")
    private Long refreshExpiredTimeMs;

    @Override
    public String generateAccessToken(User user) {
        return JwtTokenUtils.generateJwtToken(user, secretKey, accessExpiredTimeMs);
    }

    @Override
    public String generateAndSaveRefreshToken(User user) {
        String refreshToken = JwtTokenUtils.generateJwtToken(user, secretKey, refreshExpiredTimeMs);

        redisTemplate.opsForValue().set(user.getId().toString(), refreshToken);

        return refreshToken;
    }

    @Override
    public boolean validateAccessToken(String token) {
        return false;
    }

    @Override
    public boolean validateRefreshToken(String token) {
        return false;
    }
}
