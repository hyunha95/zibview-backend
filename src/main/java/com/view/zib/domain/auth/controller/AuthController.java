package com.view.zib.domain.auth.controller;

import com.view.zib.domain.auth.controller.request.LoginRequest;
import com.view.zib.domain.auth.controller.response.LoginResponse;
import com.view.zib.domain.auth.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse loginOrCreateUser(@RequestBody LoginRequest request) {
        return authService.loginOrCreateUser(request);
    }

    @GetMapping("/anonymous/cookies")
    public void createAnonymousUserCookie(HttpServletResponse response) {
        log.info("createAnonymousUserCookie");
        ResponseCookie cookie = ResponseCookie.from("anonymousUserUUID", UUID.randomUUID().toString())
                .httpOnly(false)
                .sameSite("None")
                .maxAge(60 * 60 * 24 * 365)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

}
