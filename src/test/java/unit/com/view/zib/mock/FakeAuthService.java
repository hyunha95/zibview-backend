package com.view.zib.mock;

import com.view.zib.domain.auth.controller.request.LoginRequest;
import com.view.zib.domain.auth.controller.request.RefreshRequest;
import com.view.zib.domain.auth.controller.response.LoginResponse;
import com.view.zib.domain.auth.controller.response.RefreshResponse;
import com.view.zib.domain.auth.service.AuthService;
import com.view.zib.domain.user.entity.UserEntity;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class FakeAuthService implements AuthService {
    @Override
    public LoginResponse googleLoginOrCreateUser(LoginRequest loginRequest) throws GeneralSecurityException, IOException {
        return null;
    }

    @Override
    public RefreshResponse refresh(RefreshRequest refreshRequest) {
        return null;
    }

    @Override
    public UserEntity getUser() {
        return null;
    }

    @Override
    public Long getUserId() {
        return 1L;
    }
}
