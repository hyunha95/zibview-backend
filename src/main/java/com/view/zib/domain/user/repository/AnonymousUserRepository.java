package com.view.zib.domain.user.repository;

import com.view.zib.domain.transaction.entity.TransactionApartment;
import com.view.zib.domain.user.entity.AnonymousUserHash;
import com.view.zib.domain.user.repository.redis.AnonymousUserRedisRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class AnonymousUserRepository {

    private final AnonymousUserRedisRepository anonymousUserRedisRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public Optional<AnonymousUserHash> findById(UUID anonymousUserUUID) {
        return anonymousUserRedisRepository.findById(anonymousUserUUID.toString());
    }

    public void addSet(UUID anonymousUserUUID, Set<Long> searchedJibunIds, Duration expiresIn) {
        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            for (Long searchedJibunId : searchedJibunIds) {
                connection.setCommands().sAdd(anonymousUserUUID.toString().getBytes(), searchedJibunId.toString().getBytes());
            }
            return null;
        });
        redisTemplate.expire(anonymousUserUUID.toString(), expiresIn);
    }

    public Set<Long> members(UUID anonymousUserUUID) {
        Set<Object> range = redisTemplate
                .opsForSet()
                .members(anonymousUserUUID.toString());

        if (CollectionUtils.isNotEmpty(range)) {
            return range.stream()
                    .map(jibun -> Long.parseLong(jibun.toString()))
                    .collect(Collectors.toSet());
        }

        return new HashSet<>();
    }

    public void delete(UUID anonymousUserUUID) {
        redisTemplate.delete(anonymousUserUUID.toString());
    }

    public List<TransactionApartment> findByJibunIdInAndYearMonthGroupByJibunId(Set<Long> jibunIds) {
        List<Object> objects = redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            for (Long jibunId : jibunIds) {
                connection.sMembers(String.format("transactionApartment:%s", jibunId).getBytes());
            }
            return null;
        });

        return objects.stream()
                .map(o -> (TransactionApartment) o)
                .toList();
    }
}
