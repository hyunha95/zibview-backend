package com.view.zib.domain.auth.service.impl;

import com.view.zib.domain.auth.controller.request.LoginRequest;
import com.view.zib.domain.auth.controller.response.LoginResponse;
import com.view.zib.domain.auth.service.AuthService;
import com.view.zib.domain.user.entity.User;
import com.view.zib.domain.user.repository.UserRepository;
import com.view.zib.global.common.ClockHolder;
import com.view.zib.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final ClockHolder clockHolder;

    @Override
    public User getCurrentUser() {
        return userRepository.findByEmail(this.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", this.getEmail()));
    }

    /**
     * Get the JWT from the SecurityContextHolder
     * or
     * Use "@AuthenticationPrincipal Jwt jwt" in the handler method signature
     */
    @Override
    public Jwt getJwt() {
        return (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public String getSubject() {
        return getJwt().getSubject();
    }

    @Override
    public String getEmail() {
        return (String) getJwt().getClaims().get("email");
    }

    @Transactional
    @Override
    public LoginResponse loginOrCreateUser(LoginRequest request) {
        LoginResponse loginResponse = new LoginResponse();

        userRepository.findByEmail(request.email())
                .ifPresentOrElse(login(), createUser(request, loginResponse));

        return loginResponse;
    }

    private Runnable createUser(LoginRequest request, LoginResponse response) {
        return () -> {
            log.info("User {} created", request.email());
            User user = User.of(request, clockHolder, this.getSubject());
            response.setNeedOnboarding(true);
            userRepository.save(user);
        };
    }

    private Consumer<User> login() {
        return user -> {
            log.info("User {} logged in", user.getEmail());
            user.updateLastLoginAt(clockHolder);
        };
    }
}
