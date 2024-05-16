package com.view.zib.domain.user.domain;

import lombok.Builder;

public record GoogleUser(
        String email,
        boolean emailVerified,
        String name,
        String pictureUrl,
        String familyName,
        String givenName
) implements AbstractUser {

    @Builder
    public GoogleUser {}
}
