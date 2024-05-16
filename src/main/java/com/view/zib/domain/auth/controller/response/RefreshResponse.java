package com.view.zib.domain.auth.controller.response;

import lombok.Builder;

public record RefreshResponse(String accessToken, String tokenType, long expiresIn) {

    @Builder
    public RefreshResponse {
    }
}
