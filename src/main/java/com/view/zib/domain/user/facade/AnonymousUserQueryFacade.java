package com.view.zib.domain.user.facade;

import com.view.zib.domain.user.entity.AnonymousUserHash;
import com.view.zib.domain.user.service.AnonymousUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class AnonymousUserQueryFacade {

    private final AnonymousUserService anonymousUserService;

    public AnonymousUserHash getById(UUID anonymousUserUUID) {
        return anonymousUserService.getById(anonymousUserUUID);
    }
}
