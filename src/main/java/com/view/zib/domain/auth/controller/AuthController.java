package com.view.zib.domain.auth.controller;

import com.view.zib.domain.auth.controller.request.LoginRequest;
import com.view.zib.domain.auth.controller.response.LoginResponse;
import com.view.zib.domain.auth.service.AuthService;
import com.view.zib.domain.user.facade.AnonymousUserCommandFacade;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;
    private final AnonymousUserCommandFacade anonymousUserCommandFacade;

    @PostMapping("/login")
    public LoginResponse loginOrCreateUser(@RequestBody LoginRequest request) {
        return authService.loginOrCreateUser(request);
    }

    @GetMapping("/anonymous/cookies")
    public ResponseEntity<Map<String, String>> createAnonymousUserCookie(HttpServletResponse response) {
        log.info("createAnonymousUserCookie");
        UUID anonymousUserUUID = UUID.randomUUID();

        anonymousUserCommandFacade.addSet(anonymousUserUUID, Collections.emptySet());

        ResponseCookie cookie = ResponseCookie.from("anonymousUserUUID", anonymousUserUUID.toString())
                .httpOnly(false)
                .secure(true)
                .sameSite("None")
                .maxAge(60 * 60 * 24 * 365)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok(Map.of("anonymousUserUUID", cookie.getValue()));
    }
}
