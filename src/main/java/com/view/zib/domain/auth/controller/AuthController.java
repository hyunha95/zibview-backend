package com.view.zib.domain.auth.controller;

import com.view.zib.domain.auth.controller.request.RefreshRequest;
import com.view.zib.domain.auth.controller.response.RefreshResponse;
import com.view.zib.domain.auth.service.AuthService;
import com.view.zib.domain.auth.controller.request.LoginRequest;
import com.view.zib.domain.auth.controller.response.LoginResponse;
import com.view.zib.global.common.PaginationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login/google")
    public LoginResponse googleLoginOrCreateUser(
            @RequestBody LoginRequest loginRequest
    ) throws GeneralSecurityException, IOException {
        return authService.googleLoginOrCreateUser(loginRequest);
    }

    @PostMapping("/refresh/google")
    public RefreshResponse refresh(@RequestBody RefreshRequest refreshRequest) {
        return authService.refresh(refreshRequest);
    }
}
