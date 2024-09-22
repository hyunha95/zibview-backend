package com.view.zib.domain.transaction.entity;

import com.view.zib.domain.address.entity.Jibun;
import com.view.zib.domain.client.vworld.dto.OfficeTelTransactionClientDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "building_transaction")
public class BuildingTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "building_transaction_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jibun_id")
    private Jibun jibun;

    @Size(max = 255)
    @NotNull
    @Column(name = "dtype", nullable = false)
    private String dtype;

    @Size(max = 255)
    @Column(name = "sgg_cd")
    private String sggCd;

    @Size(max = 255)
    @Column(name = "sgg_nm")
    private String sggNm;

    @Size(max = 255)
    @Column(name = "umd_nm")
    private String umdNm;

    @Size(max = 255)
    @Column(name = "jibun_main")
    private String jibunMain;

    @Size(max = 255)
    @Column(name = "offi_nm")
    private String offiNm;

    @Size(max = 255)
    @Column(name = "exclu_use_ar")
    private String excluUseAr;

    @Size(max = 255)
    @Column(name = "deal_year")
    private String dealYear;

    @Size(max = 255)
    @Column(name = "deal_month")
    private String dealMonth;

    @Size(max = 255)
    @Column(name = "deal_day")
    private String dealDay;

    @Size(max = 255)
    @Column(name = "deposit")
    private String deposit;

    @Size(max = 255)
    @Column(name = "monthly_rent")
    private String monthlyRent;

    @Size(max = 255)
    @Column(name = "floor")
    private String floor;

    @Size(max = 255)
    @Column(name = "build_year")
    private String buildYear;

    @Size(max = 255)
    @Column(name = "contract_term")
    private String contractTerm;

    @Size(max = 255)
    @Column(name = "contract_type")
    private String contractType;

    @Size(max = 255)
    @Column(name = "use_rr_right")
    private String useRrRight;

    @Size(max = 255)
    @Column(name = "pre_deposit")
    private String preDeposit;

    @Size(max = 255)
    @Column(name = "pre_monthly_rent")
    private String preMonthlyRent;

    public static BuildingTransaction from(OfficeTelTransactionClientDTO.Item item) {
        return BuildingTransaction.builder()
                .sggCd(item.sggCd())
                .sggNm(item.sggNm())
                .umdNm(item.umdNm())
                .jibunMain(item.jibun())
                .offiNm(item.offiNm())
                .excluUseAr(item.excluUseAr())
                .dealYear(item.dealYear())
                .dealMonth(item.dealMonth())
                .dealDay(item.dealDay())
                .deposit(item.deposit())
                .monthlyRent(item.monthlyRent())
                .floor(item.floor())
                .buildYear(item.buildYear())
                .contractTerm(item.contractTerm())
                .contractType(item.contractType())
                .useRrRight(item.useRRRight())
                .preDeposit(item.preDeposit())
                .preMonthlyRent(item.preMonthlyRent())
                .dtype("OFFICETEL")
                .build();
    }


}