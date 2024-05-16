package com.view.zib.domain.user.enums;

import lombok.Getter;

@Getter
public enum Role {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }
}
