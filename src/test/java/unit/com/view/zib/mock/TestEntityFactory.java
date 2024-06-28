package com.view.zib.mock;

import com.view.zib.domain.user.entity.User;

public class TestEntityFactory {

    public static User createUser() {
        return User.builder()
                .email("test@test.com")
                .familyName("노")
                .givenName("현하")
                .password("1234")
                .build();
    }
}
