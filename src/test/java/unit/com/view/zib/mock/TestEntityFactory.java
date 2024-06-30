package com.view.zib.mock;

import com.view.zib.domain.user.entity.User;

import java.time.LocalDateTime;

public class TestEntityFactory {

    public static User createUser() {
        return User.builder()
                .subject("subject")
                .pictureUrl("pictureUrl")
                .email("email")
                .password(null)
                .name("name")
                .givenName("givenName")
                .familyName("familyName")
                .lastLoginAt(LocalDateTime.now())
                .enabled(true)
                .authorities("ROLE_USER,ROLE_ADMIN")
                .build();
    }

    public static User createUser(String email) {
        return User.builder()
                .subject("subject")
                .pictureUrl("pictureUrl")
                .email(email)
                .password(null)
                .name("name")
                .givenName("givenName")
                .familyName("familyName")
                .lastLoginAt(LocalDateTime.now())
                .enabled(true)
                .authorities("ROLE_USER,ROLE_ADMIN")
                .build();
    }
}
