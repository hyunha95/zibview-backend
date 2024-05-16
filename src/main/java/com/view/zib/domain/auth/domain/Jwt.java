package com.view.zib.domain.auth.domain;

import com.view.zib.domain.auth.enums.TokenType;
import lombok.Builder;

public record Jwt(
        Long id,
        String accessToken,
        String refreshToken,
        TokenType tokenType,
        Long expiresIn
) {

    @Builder
    public Jwt {
    }
}
