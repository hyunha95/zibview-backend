package com.view.zib.domain.user.entity;

import com.view.zib.domain.auth.controller.request.LoginRequest;
import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.location.entity.Location;
import com.view.zib.domain.user.enums.Role;
import com.view.zib.global.common.ClockHolder;
import com.view.zib.global.jpa.BaseEntity;
import io.jsonwebtoken.lang.Collections;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Builder // @VisibleForTesting
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users")
@Entity
public class User extends BaseEntity implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToOne(mappedBy = "user")
    private Location location;

    private String subject;
    private String pictureUrl;
    private String email;
    private String password;
    private String name;
    private String givenName;
    private String familyName;
    private LocalDateTime lastLoginAt;
    @Column(length = 1, columnDefinition = "tinyint(1)")
    private boolean enabled;
    private String authorities;

    public static User of(LoginRequest request, ClockHolder clockHolder, String subject) {
        return User.builder()
                .subject(subject)
                .email(request.email())
                .name(request.name())
                .givenName(request.givenName())
                .familyName(request.familyName())
                .pictureUrl(request.picture())
                .enabled(true)
                .lastLoginAt(clockHolder.now())
                .authorities(Role.ROLE_USER.name())
                .build();
    }

    public void updateLastLoginAt(ClockHolder clockHolder) {
        this.lastLoginAt = clockHolder.now();
    }

    public boolean isMyImage(Image image) {
        return image.getUser() == this;
    }

    public boolean isMyImages(List<Image> images) {
        if (Collections.isEmpty(images)) return true;

        return images.stream().allMatch(this::isMyImage);
    }

    // ===============
    // 연관 관계 세팅
    // ===============
    public void addEntity(Location location) {
        this.location = location;
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
}
