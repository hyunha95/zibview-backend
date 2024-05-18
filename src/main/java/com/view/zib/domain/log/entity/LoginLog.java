package com.view.zib.domain.log.entity;

import com.view.zib.global.jpa.UnmodifiableEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Table(name = "log_login")
@Entity
public class LoginLog extends UnmodifiableEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_login_id")
    private Long id;
    private String email;
    private LocalDateTime loginTime;
}
