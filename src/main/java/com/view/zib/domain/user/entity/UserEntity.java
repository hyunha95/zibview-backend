package com.view.zib.domain.user.entity;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.view.zib.domain.user.domain.User;
import com.view.zib.domain.user.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "users")
@Entity
public class UserEntity implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userEntity")
    private List<UserAddress> userAddresses = new ArrayList<>();

    private String pictureUrl;
    private String email;
    private String password;
    private String name;
    private String firstName;
    private String lastName;
    private LocalDateTime lastLoginAt;
    @Column(length = 1, columnDefinition = "tinyint(1)")
    private boolean enabled;
    private String authorities;
    private String refreshToken;

    public static UserEntity from(User user) {
        return UserEntity.builder()
                .id(user.id())
                .pictureUrl(user.pictureUrl())
                .email(user.email())
                .password(user.password())
                .name(user.name())
                .firstName(user.firstName())
                .lastName(user.lastName())
                .enabled(user.enabled())
                .lastLoginAt(user.lastLoginAt())
                .authorities(user.authorities())
                .build();
    }

    public static UserEntity from(GoogleIdToken.Payload payload, LocalDateTime lastLoginAt, String refreshToken) {
        return UserEntity.builder()
                .email(payload.getEmail())
                .name((String) payload.get("name"))
                .firstName((String) payload.get("given_name"))
                .lastName((String) payload.get("family_name"))
                .pictureUrl((String) payload.get("picture"))
                .enabled(true)
                .lastLoginAt(lastLoginAt)
                .refreshToken(refreshToken)
                .authorities(Role.ROLE_USER.name())
                .build();
    }

    public void updateLastLoginAt(LocalDateTime now) {
        this.lastLoginAt = now;
    }

    // ===============
    // Spring Security
    // ===============
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(this.authorities.split(","))
                .map(authority -> (GrantedAuthority) () -> authority)
                .toList();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
