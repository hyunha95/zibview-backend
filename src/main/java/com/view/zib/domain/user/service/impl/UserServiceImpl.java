package com.view.zib.domain.user.service.impl;

import com.view.zib.domain.user.entity.User;
import com.view.zib.domain.user.repository.UserRepository;
import com.view.zib.domain.user.service.UserService;
import com.view.zib.global.exception.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getById(Long userId) {
        return findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", userId));
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User getByEmail(String email) {
        return findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", email));
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
