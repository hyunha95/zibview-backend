package com.view.zib.domain.user.service.impl;

import com.view.zib.domain.user.entity.UserEntity;
import com.view.zib.domain.user.repository.UserRepository;
import com.view.zib.domain.user.service.UserService;
import com.view.zib.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity getById(Long userId) {
        return findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", userId));
    }

    @Override
    public Optional<UserEntity> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public UserEntity getBySubject(String subject) {
        return this.findBySubject(subject).orElseThrow(() -> new ResourceNotFoundException("User", subject));
    }

    public Optional<UserEntity> findBySubject(String subject) {
        return userRepository.findBySubject(subject);
    }

}
