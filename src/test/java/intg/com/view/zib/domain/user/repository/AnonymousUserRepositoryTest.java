package com.view.zib.domain.user.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AnonymousUserRepositoryTest {

    @Autowired
    private AnonymousUserRepository anonymousUserRepository;

    @Test
    void rightPushAll() {
        UUID key = UUID.randomUUID();
        List<Long> jibunIds = List.of(1L, 2L, 3L);

//        anonymousUserRepository.rightPushAll(key, jibunIds, Duration.ofDays(1));
//
//
//        List<Long> result = anonymousUserRepository.range(key);
    }
}