package com.view.zib.domain.user.service;

import com.view.zib.domain.user.entity.UserEntity;

import java.util.Optional;

public interface UserService {
    UserEntity getById(Long userId);
    Optional<UserEntity> findById(Long userId);
}
