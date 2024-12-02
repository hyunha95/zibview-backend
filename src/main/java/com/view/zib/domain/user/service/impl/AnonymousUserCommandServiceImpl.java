package com.view.zib.domain.user.service.impl;

import com.view.zib.domain.transaction.entity.TransactionApartment;
import com.view.zib.domain.user.repository.AnonymousUserRepository;
import com.view.zib.domain.user.service.AnonymousUserCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Transactional
@RequiredArgsConstructor
@Service
public class AnonymousUserCommandServiceImpl implements AnonymousUserCommandService {

    private final AnonymousUserRepository anonymousUserRepository;

    @Override
    public void addSet(UUID anonymousUserUUID, Set<Long> jibunIds) {
        anonymousUserRepository.addSet(anonymousUserUUID, jibunIds, Duration.ofDays(1));
    }

    @Override
    public void delete(UUID anonymousUserUUID) {
        anonymousUserRepository.delete(anonymousUserUUID);
    }

    @Override
    public List<TransactionApartment> findByJibunIdInAndYearMonthGroupByJibunId(Set<Long> jibunIds) {
        return anonymousUserRepository.findByJibunIdInAndYearMonthGroupByJibunId(jibunIds);
    }
}
