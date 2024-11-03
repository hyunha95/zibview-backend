package com.view.zib.domain.user.service.impl;

import com.view.zib.domain.transaction.entity.TransactionApartment;
import com.view.zib.domain.user.entity.AnonymousUserHash;
import com.view.zib.domain.user.repository.AnonymousUserRepository;
import com.view.zib.domain.user.service.AnonymousUserQueryService;
import com.view.zib.global.exception.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AnonymousUserQueryServiceImpl implements AnonymousUserQueryService {

    private final AnonymousUserRepository anonymousUserRepository;

    @Override
    public AnonymousUserHash getById(UUID anonymousUserUUID) {
        return findById(anonymousUserUUID);
    }

    public AnonymousUserHash findById(UUID anonymousUserUUID) {
        return anonymousUserRepository.findById(anonymousUserUUID)
                .orElseThrow(() -> new ResourceNotFoundException("AnonymousUser not found", "id", anonymousUserUUID.toString()));
    }

    public Set<Long> members(UUID anonymousUserUUID) {
        return anonymousUserRepository.members(anonymousUserUUID);
    }

    @Override
    public List<TransactionApartment> findByJibunIdInAndYearMonthGroupByJibunId(Set<Long> foundJibunIds) {
        return anonymousUserRepository.findByJibunIdInAndYearMonthGroupByJibunId(foundJibunIds);
    }
}
