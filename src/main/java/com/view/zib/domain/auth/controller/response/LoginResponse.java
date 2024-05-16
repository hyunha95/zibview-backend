package com.view.zib.domain.auth.controller.response;

import lombok.Data;

@Data
public class LoginResponse{
    private String accessToken;
    private String tokenType;
    private long expiresIn;
    private String refreshToken;
    private boolean loginSuccess;
    private boolean needOnboarding;
    private String email;
    private String pictureUrl;
    private String name;
    private String lastName;
    private String firstName;
}
