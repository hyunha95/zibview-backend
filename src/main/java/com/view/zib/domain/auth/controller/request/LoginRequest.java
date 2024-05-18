package com.view.zib.domain.auth.controller.request;

public record LoginRequest(
        String email,
        boolean emailVerified,
        String familyName,
        String givenName,
        String locale,
        String name,
        String nickname,
        String picture,
        String sub,
        String updatedAt
) {
}
