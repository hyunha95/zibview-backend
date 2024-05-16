package com.view.zib.domain.auth.domain;

import lombok.Builder;

public record GoogleRefreshTokenRequest(
        String clientId,
        String clientSecret,
        String refreshToken,
        String grantType
) {

    @Builder
    public GoogleRefreshTokenRequest {
        grantType =  "refresh_token";
    }
}
