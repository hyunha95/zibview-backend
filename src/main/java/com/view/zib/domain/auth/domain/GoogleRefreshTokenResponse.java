package com.view.zib.domain.auth.domain;

public record GoogleRefreshTokenResponse(String accessToken, long expiresIn, String scope, String tokenType) {
}
