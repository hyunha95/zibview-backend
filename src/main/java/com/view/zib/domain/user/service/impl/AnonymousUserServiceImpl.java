package com.view.zib.domain.user.service.impl;

import com.view.zib.domain.user.entity.AnonymousUserHash;
import com.view.zib.domain.user.repository.AnonymousUserRepository;
import com.view.zib.domain.user.service.AnonymousUserService;
import com.view.zib.global.exception.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AnonymousUserServiceImpl implements AnonymousUserService {

    private final AnonymousUserRepository anonymousUserRepository;

    @Override
    public AnonymousUserHash getById(UUID anonymousUserUUID) {
        return findById(anonymousUserUUID);
    }

    public AnonymousUserHash findById(UUID anonymousUserUUID) {
        return anonymousUserRepository.findById(anonymousUserUUID)
                .orElseThrow(() -> new ResourceNotFoundException("AnonymousUser not found", "id", anonymousUserUUID.toString()));
    }
}
