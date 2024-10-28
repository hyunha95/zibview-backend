package com.view.zib.domain.user.service;

import com.view.zib.domain.user.entity.AnonymousUserHash;

import java.util.UUID;

public interface AnonymousUserService {
    AnonymousUserHash getById(UUID anonymousUserUUID);
}
