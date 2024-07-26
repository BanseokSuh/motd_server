package com.banny.motd.domain.user.application.repository;

import com.banny.motd.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserCacheRepository {

    private final RedisTemplate<String, User> redisTemplate;
    private final static Duration USER_CACHE_TTL = Duration.ofDays(3); // 3 days

    public void setUser(User user) {
        String key = getKey(user.getId());
        redisTemplate.opsForValue().set(key, user, USER_CACHE_TTL);

        log.info("Set User into Redis {}, {}", key, user);
    }

    public Optional<User> getUser(Long userId) {
        String key = getKey(userId);
        User user = redisTemplate.opsForValue().get(key);

        log.info("Get data from Redis {}, {}", key, user);

        return Optional.ofNullable(user);
    }

    private String getKey(Long userId) {
        return "USER:" + userId;
    }
}
