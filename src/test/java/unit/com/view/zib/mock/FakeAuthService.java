package com.view.zib.mock;

import com.view.zib.domain.auth.controller.request.LoginRequest;
import com.view.zib.domain.auth.controller.response.LoginResponse;
import com.view.zib.domain.auth.service.AuthService;
import com.view.zib.domain.user.entity.User;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.LocalDateTime;

public class FakeAuthService implements AuthService {

    @Override
    public boolean isLoggedIn() {
        return false;
    }

    @Override
    public Long getCurrentUserId() {
        return 0L;
    }

    @Override
    public Jwt getJwt() {
        return null;
    }

    @Override
    public String getSubject() {
        return "";
    }

    @Override
    public LoginResponse loginOrCreateUser(LoginRequest request) {
        return null;
    }

    @Override
    public User getCurrentUser() {
        return User.builder()
                .id(1L)
                .subject("subject")
                .pictureUrl("pictureUrl")
                .email("email")
                .password(null)
                .name("name")
                .givenName("givenName")
                .familyName("familyName")
                .lastLoginAt(LocalDateTime.now())
                .enabled(true)
                .authorities("ROLE_USER,ROLE_ADMIN")
                .build();
    }

    @Override
    public String getEmail() {
        return "";
    }
}
