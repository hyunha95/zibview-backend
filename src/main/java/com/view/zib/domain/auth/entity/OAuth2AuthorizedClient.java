package com.view.zib.domain.auth.entity;

import com.view.zib.domain.auth.enums.ClientRegistrationId;
import com.view.zib.domain.auth.enums.TokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "oauth2_authorized_client")
@Entity
public class OAuth2AuthorizedClient {

    @Id
    private String email;

    @Enumerated(value = EnumType.STRING)
    private ClientRegistrationId clientRegistrationId;

    @Enumerated(value = EnumType.STRING)
    private TokenType accessTokenType;
    private String accessTokenValue;
    private Long accessTokenIssuedAt;
    private Long accessTokenExpiresAt;
    private String accessTokenScope;
    private String refreshTokenValue;
    private Long refreshTokenIssuedAt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
