package com.view.zib.domain.user.repository.redis;

import com.view.zib.domain.user.entity.AnonymousUserHash;
import org.springframework.data.repository.CrudRepository;

public interface AnonymousUserRedisRepository extends CrudRepository<AnonymousUserHash, String> {
}
