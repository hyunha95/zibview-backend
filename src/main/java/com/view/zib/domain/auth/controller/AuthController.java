package com.view.zib.domain.auth.controller;

import com.view.zib.domain.auth.controller.request.LoginRequest;
import com.view.zib.domain.auth.controller.response.LoginResponse;
import com.view.zib.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse loginOrCreateUser(@RequestBody LoginRequest request) {
        return authService.loginOrCreateUser(request);
    }
}
