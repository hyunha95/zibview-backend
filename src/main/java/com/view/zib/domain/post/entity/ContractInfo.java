package com.view.zib.domain.post.entity;

import com.view.zib.domain.post.controller.request.PostRequest;
import com.view.zib.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class ContractInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_info_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_post_id")
    private SubPost subPost;

    private BigDecimal deposit;
    private BigDecimal monthlyFee;
    private BigDecimal maintenanceFee;

    public static ContractInfo from(PostRequest.ContractInfo contractInfo) {
        return com.view.zib.domain.post.entity.ContractInfo.builder()
                .deposit(BigDecimal.valueOf(contractInfo.getContractPrice().getDeposit()))
                .monthlyFee(BigDecimal.valueOf(contractInfo.getContractPrice().getMonthlyFee()))
                .maintenanceFee(BigDecimal.valueOf(contractInfo.getContractPrice().getMaintenanceFee()))
                .build();
    }

    public void addEntity(SubPost subPost) {
        this.subPost = subPost;
    }
}
