package com.view.zib.domain.user.repository;

import com.view.zib.domain.user.entity.AnonymousUserHash;
import com.view.zib.domain.user.repository.redis.AnonymousUserRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class AnonymousUserRepository {

    private final AnonymousUserRedisRepository anonymousUserRedisRepository;

    public Optional<AnonymousUserHash> findById(UUID anonymousUserUUID) {
        return anonymousUserRedisRepository.findById(anonymousUserUUID.toString());
    }
}
