package com.view.zib.domain.auth.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.view.zib.domain.auth.controller.request.LoginRequest;
import com.view.zib.domain.auth.controller.request.RefreshRequest;
import com.view.zib.domain.auth.controller.response.LoginResponse;
import com.view.zib.domain.auth.controller.response.RefreshResponse;
import com.view.zib.domain.auth.domain.*;
import com.view.zib.domain.auth.entity.OAuth2AuthorizedClient;
import com.view.zib.domain.auth.enums.ClientRegistrationId;
import com.view.zib.domain.auth.enums.TokenType;
import com.view.zib.domain.auth.repository.AuthRepository;
import com.view.zib.domain.auth.repository.OAuth2AuthorizedClientRepository;
import com.view.zib.domain.auth.service.AuthService;
import com.view.zib.domain.user.entity.UserEntity;
import com.view.zib.domain.user.repository.UserRepository;
import com.view.zib.global.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;

    private final AuthRepository authRepository;
    private final OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository;
    private final UserRepository userRepository;

    private final Clock clock;

    @Value("${oauth2.google.client-id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${oauth2.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;

    @Override
    public LoginResponse googleLoginOrCreateUser(LoginRequest loginRequest) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(GOOGLE_CLIENT_ID))
                .build();
        GoogleIdToken googleIdToken = verifier.verify(loginRequest.idToken());
        if (googleIdToken == null) {
            throw new RuntimeException("Invalid ID token.");
        }

        GoogleIdToken.Payload payload = googleIdToken.getPayload();

        RestTemplate restTemplate = new RestTemplate();
        GoogleTokenRequest request = GoogleTokenRequest.builder()
                .code(loginRequest.serverAuthCode())
                .client_id(GOOGLE_CLIENT_ID)
                .client_secret(GOOGLE_CLIENT_SECRET)
                .redirect_uri("http://localhost:8080/login/oauth2/code/google")
                .grant_type("authorization_code")
                .build();

        ResponseEntity<GoogleTokenResponse> stringResponseEntity = restTemplate.postForEntity("https://oauth2.googleapis.com/token", request, GoogleTokenResponse.class);
        GoogleTokenResponse googleTokenResponse = stringResponseEntity.getBody();

        System.out.println(googleTokenResponse);

        LoginResponse loginResponse = new LoginResponse();
        authRepository.findByEmail(payload.getEmail()).ifPresentOrElse(updateUser(loginResponse, googleTokenResponse.getRefreshToken()), createUser(payload, loginResponse, googleTokenResponse.getRefreshToken()));
        loginResponse.setLoginSuccess(true);
        loginResponse.setEmail(payload.getEmail());
        loginResponse.setPictureUrl((String) payload.get("picture"));
        loginResponse.setName((String) payload.get("name"));
        loginResponse.setLastName((String) payload.get("family_name"));
        loginResponse.setFirstName((String) payload.get("given_name"));
        loginResponse.setTokenType("Bearer");
        loginResponse.setRefreshToken(googleTokenResponse.getRefreshToken());

        OAuth2AuthorizedClient oAuth2AuthorizedClient = OAuth2AuthorizedClient.builder()
                .email(payload.getEmail())
                .clientRegistrationId(ClientRegistrationId.GOOGLE)
                .accessTokenType(TokenType.BEARER)
                .accessTokenValue(googleTokenResponse.getAccessToken())
                .accessTokenIssuedAt(payload.getIssuedAtTimeSeconds())
                .accessTokenExpiresAt(payload.getExpirationTimeSeconds())
                .accessTokenScope(googleTokenResponse.getScope())
                .refreshTokenValue(googleTokenResponse.getRefreshToken())
                .refreshTokenIssuedAt(payload.getExpirationTimeSeconds())
                .createdAt(LocalDateTime.now(clock))
                .modifiedAt(LocalDateTime.now(clock))
                .build();

        oAuth2AuthorizedClientRepository.save(oAuth2AuthorizedClient);

        return loginResponse;
    }

    @Override
    public RefreshResponse refresh(RefreshRequest refreshRequest) {
        UserEntity userEntity = userRepository.findByRefreshToken(refreshRequest.refreshToken())
                .orElseThrow();
        log.info("token refreshed for user: {}", userEntity.getEmail());

        RestTemplate restTemplate = new RestTemplate();
        GoogleRefreshTokenRequest request = GoogleRefreshTokenRequest.builder()
                .clientId(GOOGLE_CLIENT_ID)
                .clientSecret(GOOGLE_CLIENT_SECRET)
                .refreshToken(refreshRequest.refreshToken())
                .build();

        // check if refresh token is valid
        ResponseEntity<GoogleRefreshTokenResponse> response = restTemplate.postForEntity("https://www.googleapis.com/oauth2/v4/token", request, GoogleRefreshTokenResponse.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Invalid refresh token.");
        }

        String accessToken = jwtService.generateAccessToken(userEntity);
        return RefreshResponse.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .expiresIn(jwtService.getExpiresIn())
                .build();
    }

    @Override
    public UserEntity getUser() {
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public Long getUserId() {
        return getUser().getId();
    }

    private Runnable createUser(GoogleIdToken.Payload payload, LoginResponse loginResponse, String refreshToken) {
        return () -> {
            UserEntity userEntity = UserEntity.from(payload, LocalDateTime.now(clock), refreshToken);
            loginResponse.setAccessToken(jwtService.generateAccessToken(userEntity));
            loginResponse.setNeedOnboarding(true);
            userRepository.save(userEntity);
        };
    }

    private Consumer<UserEntity> updateUser(LoginResponse loginResponse, String refreshToken) {
        return userEntity -> {
            loginResponse.setAccessToken(jwtService.generateAccessToken(userEntity));
            userEntity.updateLastLoginAt(LocalDateTime.now(clock));
            userEntity.updateRefreshToken(refreshToken);
        };
    }
}
