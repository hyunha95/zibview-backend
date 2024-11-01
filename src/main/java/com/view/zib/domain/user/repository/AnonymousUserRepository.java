package com.view.zib.domain.user.repository;

import com.view.zib.domain.user.entity.AnonymousUserHash;
import com.view.zib.domain.user.repository.redis.AnonymousUserRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class AnonymousUserRepository {

    private final AnonymousUserRedisRepository anonymousUserRedisRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public Optional<AnonymousUserHash> findById(UUID key) {
        return anonymousUserRedisRepository.findById(key.toString());
    }

    public void rightPushAll(UUID key, List<Long> jibunIds, Duration expiresIn) {
        redisTemplate.opsForList().rightPushAll(key.toString(), jibunIds);
        redisTemplate.expireAt(key.toString(), Instant.now().plus(expiresIn));
    }

    public List<Long> range(UUID key) {


        return redisTemplate.opsForList().range(key.toString(), 0, -1).stream()
                .mapToLong(value -> Long.parseLong((String) value))
                .boxed()
                .toList();
    }
}
