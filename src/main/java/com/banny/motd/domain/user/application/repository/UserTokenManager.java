package com.banny.motd.domain.user.application.repository;

import com.banny.motd.domain.user.domain.User;
import com.banny.motd.global.enums.DeviceType;
import com.banny.motd.global.exception.ApplicationException;
import com.banny.motd.global.exception.ResultType;
import com.banny.motd.global.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository // 스프링 빈으로 등록
@RequiredArgsConstructor
public class UserTokenManager {

    private final RedisTemplate<String, String> redisTemplate;
    private final static Duration ACCESS_TOKEN_CACHE_TTL = Duration.ofMinutes(30); // 30분
    private final static Duration REFRESH_TOKEN_CACHE_TTL = Duration.ofDays(1); // 1 day

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.access-expired-time-ms}")
    private Long accessExpiredTimeMs;

    @Value("${jwt.token.refresh-expired-time-ms}")
    private Long refreshExpiredTimeMs;

    /**
     * access token 발급
     *
     * @param user 사용자 정보
     * @return access token
     */
    public String generateAccessToken(User user) {
        return JwtTokenUtils.generateJwtToken(user, secretKey, accessExpiredTimeMs);
    }

    /**
     * refresh token 발급
     *
     * @param user 사용자 정보
     * @return refresh token
     */
    public String generateRefreshToken(User user) {
        return JwtTokenUtils.generateJwtToken(user, secretKey, refreshExpiredTimeMs);
    }

    /**
     * access token redis에 저장 (중복로그인 방지용)
     *
     * @param userId 사용자 id
     * @param accessToken access token
     */
    public void saveAccessToken(Long userId, DeviceType deviceType, String accessToken) {
        String key = getAccessTokenKey(userId, deviceType.getValue());
        redisTemplate.opsForValue().set(key, accessToken, ACCESS_TOKEN_CACHE_TTL);
    }

    /**
     * refresh token redis에 저장
     *
     * @param userId 사용자 id
     * @param refreshToken refresh token
     */
    public void saveRefreshToken(Long userId, DeviceType deviceType, String refreshToken) {
        String key = getRefreshTokenKey(userId, deviceType.getValue());
        redisTemplate.opsForValue().set(key, refreshToken, REFRESH_TOKEN_CACHE_TTL);
    }

    /**
     * 이미 로그인 중인지 확인
     *
     * @param userId 사용자 id
     */
    public void checkAlreadyLoggedIn(Long userId, DeviceType deviceType) {
        String key = getAccessTokenKey(userId, deviceType.getValue());

        if (hasKey(key)) {
            throw new ApplicationException(ResultType.FAIL_ALREADY_LOGGED_IN, String.format("User %s is already logged in", userId));
        }
    }

    /**
     * token 삭제
     *
     * @param userId 사용자 id
     * @param deviceType 디바이스 타입
     */
    public void deleteToken(Long userId, DeviceType deviceType) {
        String accessTokenKey = getAccessTokenKey(userId, deviceType.getValue());
        String refreshTokenKey = getRefreshTokenKey(userId, deviceType.getValue());

        redisTemplate.delete(accessTokenKey);
        redisTemplate.delete(refreshTokenKey);
    }

    /**
     * key 존재 여부 확인
     *
     * @param key key
     * @return key 존재 여부
     */
    private boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * access token을 저장하기 위한 key 조회
     *
     * @param userId 사용자 id
     * @return access token key
     */
    private String getAccessTokenKey(Long userId, Integer deviceType) {
        return "A_TOKEN:" + userId + "-" + deviceType;
    }

    /**
     * refresh token을 저장하기 위한 key 조회
     *
     * @param userId 사용자 id
     * @return refresh token key
     */
    private String getRefreshTokenKey(Long userId, Integer deviceType) {
        return "R_TOKEN:" + userId + "-" + deviceType;
    }

}
