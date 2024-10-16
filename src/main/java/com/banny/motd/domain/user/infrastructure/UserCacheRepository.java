package com.banny.motd.domain.user.infrastructure;

import com.banny.motd.domain.user.User;
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
    private static final Duration USER_CACHE_TTL = Duration.ofDays(3); // 3 days

    /**
     * 사용자 정보를 캐시에 저장 <p>
     * 캐시 만료 시간은 3일 <p>
     * <p>
     * redisTemplate.opsForValue()는 Redis의 String 데이터 구조를 다루는 메서드를 제공 <p>
     * set, get, delete, increment, decrement 등의 메서드를 제공
     *
     * @param user 사용자 정보
     */
    public void setUser(User user) {
        String key = getKey(user.getId());
        redisTemplate.opsForValue().set(key, user, USER_CACHE_TTL);

        log.info("Set User into Redis {}, {}", key, user);
    }

    /**
     * 사용자 정보를 캐시에서 조회
     *
     * @param userId 사용자 ID
     * @return Optional<User>
     */
    public Optional<User> getUser(Long userId) {
        String key = getKey(userId);
        User user = redisTemplate.opsForValue().get(key);

        log.info("Get data from Redis {}, {}", key, user);

        return Optional.ofNullable(user);
    }

    /**
     * 사용자 정보를 캐시에서 삭제
     *
     * @param userId 사용자 ID
     */
    public void deleteUser(Long userId) {
        String key = getKey(userId);
        redisTemplate.delete(key);

        log.info("Delete data from Redis {}", key);
    }

    /**
     * 캐시에 사용되는 Key는 "USER:" + userId 형태로 생성 <p>
     * ex) USER:1, USER:2, USER:3 <p>
     * Redis Key 생성
     *
     * @param userId 사용자 ID
     * @return Redis Key
     */
    private String getKey(Long userId) {
        return "USER:" + userId;
    }

}
