package com.view.zib.domain.post.entity;

import com.view.zib.domain.post.enums.RentType;
import com.view.zib.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Rent extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rent_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_post_id")
    private SubPost subPost;

    @Enumerated(EnumType.STRING)
    private RentType rentType;

    private double deposit;        // 보증금
    private LocalDateTime depositUpdatedAt;
    private double monthlyRent;    // 월세
    private LocalDateTime monthlyRentUpdatedAt;
    private double maintenanceFee; // 관리비
    private LocalDateTime maintenanceFeeUpdatedAt;
    private double annualRent;     // 전세
    private LocalDateTime annualRentUpdatedAt;
}
