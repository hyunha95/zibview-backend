package com.view.zib.domain.auth.controller.request;

public record LoginRequest(String idToken, String registrationId, String serverAuthCode) {
}
