package com.view.zib.domain.user.service;

import com.view.zib.domain.user.entity.User;

import java.util.Optional;

public interface UserService {
    User getById(Long userId);

    Optional<User> findById(Long userId);

    User getBySubject(String subject);


}
