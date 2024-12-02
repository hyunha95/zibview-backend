package com.view.zib.domain.user.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.util.*;

@Getter
@Builder
@RedisHash("anonymousUser")
public class AnonymousUserHash {

    @Id
    private UUID id;

    @Builder.Default
    private List<Long> searchedJibunIds = new ArrayList<>();

    public void updateJibunIds(Set<Long> searchedJibunIds) {
        this.searchedJibunIds.addAll(searchedJibunIds);
    }

}
