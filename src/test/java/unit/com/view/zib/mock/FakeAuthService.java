package com.view.zib.mock;

import com.view.zib.domain.auth.controller.request.LoginRequest;
import com.view.zib.domain.auth.controller.response.LoginResponse;
import com.view.zib.domain.auth.service.AuthService;
import org.springframework.security.oauth2.jwt.Jwt;

public class FakeAuthService implements AuthService {

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
}
