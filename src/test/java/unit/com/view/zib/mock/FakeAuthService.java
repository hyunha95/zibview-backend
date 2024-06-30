package com.view.zib.mock;

import com.view.zib.domain.auth.controller.request.LoginRequest;
import com.view.zib.domain.auth.controller.response.LoginResponse;
import com.view.zib.domain.auth.service.AuthService;
import com.view.zib.domain.user.entity.User;
import org.springframework.security.oauth2.jwt.Jwt;

public class FakeAuthService implements AuthService {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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
        return user;
    }

    @Override
    public String getEmail() {
        return "";
    }
}
