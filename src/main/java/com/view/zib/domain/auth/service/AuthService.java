package com.view.zib.domain.auth.service;

import com.view.zib.domain.auth.controller.request.LoginRequest;
import com.view.zib.domain.auth.controller.response.LoginResponse;
import com.view.zib.domain.user.entity.User;
import org.springframework.security.oauth2.jwt.Jwt;

public interface AuthService {
    User getCurrentUser();

    Jwt getJwt();

    String getEmail();

    String getSubject();

    LoginResponse loginOrCreateUser(LoginRequest request);
}
