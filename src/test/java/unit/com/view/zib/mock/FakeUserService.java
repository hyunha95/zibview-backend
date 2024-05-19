package com.view.zib.mock;

import com.view.zib.domain.user.entity.UserEntity;
import com.view.zib.domain.user.service.UserService;
import com.view.zib.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class FakeUserService implements UserService {

    private final FakeUserRepository userRepository;

    @Override
    public UserEntity getBySubject(String subject) {
        return null;
    }

    @Override
    public UserEntity getById(Long userId) {
        return findById(userId).orElseThrow(() -> new ResourceNotFoundException("Users", userId));
    }

    @Override
    public Optional<UserEntity> findById(Long userId) {
        return userRepository.findById(userId);
    }
}

