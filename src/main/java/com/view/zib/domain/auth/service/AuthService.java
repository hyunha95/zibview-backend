package com.view.zib.domain.auth.service;

import com.view.zib.domain.auth.controller.request.LoginRequest;
import com.view.zib.domain.auth.controller.request.RefreshRequest;
import com.view.zib.domain.auth.controller.response.LoginResponse;
import com.view.zib.domain.auth.controller.response.RefreshResponse;
import com.view.zib.domain.user.entity.UserEntity;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface AuthService {
    LoginResponse googleLoginOrCreateUser(LoginRequest loginRequest) throws GeneralSecurityException, IOException;

    RefreshResponse refresh(RefreshRequest refreshRequest);

    UserEntity getUser();

    Long getUserId();
}
