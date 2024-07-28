package com.view.zib.domain.log.entity;

import com.view.zib.global.jpa.UnmodifiableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "log_view_count")
public class LogViewCount extends UnmodifiableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_view_count_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Size(max = 50)
    @NotNull
    @Column(name = "ip_address", nullable = false, length = 50)
    private String ipAddress;

    public static LogViewCount of(Long postId, String ipAddress) {
        LogViewCount logViewCount = new LogViewCount();
        logViewCount.postId = postId;
        logViewCount.ipAddress = ipAddress;
        return logViewCount;
    }
}