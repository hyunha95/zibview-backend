package com.view.zib.domain.user.facade;

import com.view.zib.domain.transaction.entity.TransactionApartment;
import com.view.zib.domain.user.service.AnonymousUserCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class AnonymousUserCommandFacade {

    private final AnonymousUserCommandService anonymousUserCommandService;

    public void addSet(UUID anonymousUserUUID, Set<Long> jibunIds) {
        anonymousUserCommandService.addSet(anonymousUserUUID, jibunIds);
    }

    public void delete(UUID anonymousUserUUID) {
        anonymousUserCommandService.delete(anonymousUserUUID);
    }
}
