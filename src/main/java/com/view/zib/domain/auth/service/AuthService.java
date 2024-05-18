package com.view.zib.domain.auth.service;

import com.view.zib.domain.auth.controller.request.LoginRequest;
import com.view.zib.domain.auth.controller.response.LoginResponse;
import org.springframework.security.oauth2.jwt.Jwt;

public interface AuthService {
    Jwt getJwt();

    String getSubject();

    LoginResponse loginOrCreateUser(LoginRequest request);
}
