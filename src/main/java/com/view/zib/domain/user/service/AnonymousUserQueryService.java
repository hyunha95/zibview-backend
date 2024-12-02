package com.view.zib.domain.user.service;

import com.view.zib.domain.transaction.entity.TransactionApartment;
import com.view.zib.domain.user.entity.AnonymousUserHash;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface AnonymousUserQueryService {
    AnonymousUserHash getById(UUID anonymousUserUUID);
    Set<Long> members(UUID anonymousUserUUID);

    List<TransactionApartment> findByJibunIdInAndYearMonthGroupByJibunId(Set<Long> foundJibunIds);
}
