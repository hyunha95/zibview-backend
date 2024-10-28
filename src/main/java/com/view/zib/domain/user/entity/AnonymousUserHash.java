package com.view.zib.domain.user.entity;

import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import java.util.Set;
import java.util.UUID;

@Getter
@RedisHash("anonymousUser")
public class AnonymousUserHash {

    @Id
    private UUID id;
    private Set<Long> searchedJibunIds;

    public void updateJibunIds(Set<Long> searchedJibunIds) {
        this.searchedJibunIds.addAll(searchedJibunIds);
    }
}
