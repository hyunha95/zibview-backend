package com.view.zib.domain.user.domain;

import lombok.Builder;

import java.time.LocalDateTime;

public record User(
        Long id,
        String subject,
        String pictureUrl,
        String email,
        String password,
        String name,
        String firstName,
        String lastName,
        boolean enabled,
        LocalDateTime lastLoginAt,
        String authorities,
        Long jwtId
) implements AbstractUser {

    @Builder
    public User {
    }

}
