package com.banny.motd.domain.user.application.repository;

import com.banny.motd.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class UserCacheRepository {

    private final RedisTemplate<String, User> redisTemplate;
    private final static Duration USER_CACHE_TTL = Duration.ofDays(3); // 3 days

    public void setUser(User user) {
        String key = getKey(user.getId());
        redisTemplate.opsForValue().set(key, user, USER_CACHE_TTL);
    }

    public User getUser(Long userId) {
        String key = getKey(userId);
        return redisTemplate.opsForValue().get(key);
    }

    private String getKey(Long userId) {
        return "user:" + userId.toString();
    }
}
