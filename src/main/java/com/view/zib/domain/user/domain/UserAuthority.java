package com.view.zib.domain.user.domain;

import lombok.Builder;

public record UserAuthority(
        User user,
        Authority authority
) {

    @Builder
    public UserAuthority {
    }

    public static UserAuthority from(User user, Authority authority) {
        return new UserAuthority(user, authority);
    }
}
