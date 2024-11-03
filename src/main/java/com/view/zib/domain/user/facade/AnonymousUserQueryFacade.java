package com.view.zib.domain.user.facade;

import com.view.zib.domain.transaction.entity.TransactionApartment;
import com.view.zib.domain.user.entity.AnonymousUserHash;
import com.view.zib.domain.user.service.AnonymousUserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class AnonymousUserQueryFacade {

    private final AnonymousUserQueryService anonymousUserQueryService;

    public AnonymousUserHash getById(UUID anonymousUserUUID) {
        return anonymousUserQueryService.getById(anonymousUserUUID);
    }

    public Set<Long> members(UUID anonymousUserUUID) {
        return anonymousUserQueryService.members(anonymousUserUUID);
    }

    public List<TransactionApartment> findByJibunIdInAndYearMonthGroupByJibunId(Set<Long> foundJibunIds) {
        return anonymousUserQueryService.findByJibunIdInAndYearMonthGroupByJibunId(foundJibunIds);
    }
}
