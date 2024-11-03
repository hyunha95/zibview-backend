package com.view.zib.domain.user.service;

import com.view.zib.domain.transaction.entity.TransactionApartment;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface AnonymousUserCommandService {
    void addSet(UUID anonymousUserUUID, Set<Long> jibunIds);

    void delete(UUID anonymousUserUUID);

    List<TransactionApartment> findByJibunIdInAndYearMonthGroupByJibunId(Set<Long> jibunIds);
}
