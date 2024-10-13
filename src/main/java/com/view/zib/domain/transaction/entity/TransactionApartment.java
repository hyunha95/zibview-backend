package com.view.zib.domain.transaction.entity;

import com.view.zib.domain.address.entity.Jibun;
import com.view.zib.domain.client.vworld.dto.ApartmentTransactionResponse;
import com.view.zib.global.jpa.TimeEntity;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "transaction_apartment")
public class TransactionApartment extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_apartment_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jibun_id")
    private Jibun jibun;

    @Size(max = 5)
    @NotNull
    @Column(name = "sgg_code", nullable = false, length = 5)
    private String sggCode;

    @Size(max = 8)
    @NotNull
    @Column(name = "emd_code", nullable = false, length = 8)
    private String emdCode;

    @Size(max = 1)
    @Column(name = "land_code", length = 1)
    private String landCode;

    @Size(max = 4)
    @Column(name = "bonbun", length = 4)
    private String bonbun;

    @Size(max = 4)
    @Column(name = "bubun", length = 4)
    private String bubun;

    @Size(max = 100)
    @Column(name = "road_name", length = 100)
    private String roadName;

    @Size(max = 5)
    @Column(name = "road_name_sgg_code", length = 5)
    private String roadNameSggCode;

    @Size(max = 7)
    @Column(name = "road_name_code", length = 7)
    private String roadNameCode;

    @Size(max = 2)
    @Column(name = "road_name_seq", length = 2)
    private String roadNameSeq;

    @Size(max = 1)
    @Column(name = "road_nameb_code", length = 1)
    private String roadNamebCode;

    @Size(max = 5)
    @Column(name = "road_name_bonbun", length = 5)
    private String roadNameBonbun;

    @Size(max = 5)
    @Column(name = "road_name_bubun", length = 5)
    private String roadNameBubun;

    @Size(max = 60)
    @Column(name = "legal_dong_name", length = 60)
    private String legalDongName;

    @Size(max = 100)
    @Column(name = "apartment_name", length = 100)
    private String apartmentName;

    @Size(max = 20)
    @Column(name = "jibun", length = 20)
    private String jibunNumber;

    @Column(name = "exclusive_use_area")
    private BigDecimal exclusiveUseArea;

    @NotNull
    @Column(name = "deal_year", nullable = false)
    private Integer dealYear;

    @NotNull
    @Column(name = "deal_month", nullable = false)
    private Integer dealMonth;

    @NotNull
    @Column(name = "deal_day", nullable = false)
    private Integer dealDay;

    @Size(max = 40)
    @NotNull
    @Column(name = "deal_amount", nullable = false, length = 40)
    private String dealAmount;

    private Integer floor;

    @Size(max = 4)
    @Column(name = "built_year", length = 4)
    private String builtYear;

    @Size(max = 20)
    @NotNull
    @Column(name = "apartment_seq", nullable = false, length = 20)
    private String apartmentSeq;

    @Size(max = 1)
    @Column(name = "cancel_deal_type", length = 1)
    private String cancelDealType;

    @Size(max = 8)
    @Column(name = "cancel_deal_day", length = 8)
    private String cancelDealDay;

    @Size(max = 10)
    @Column(name = "deal_gbn", length = 10)
    private String dealGbn;

    @Size(max = 3000)
    @Column(name = "estate_agent_sgg_name", length = 3000)
    private String estateAgentSggName;

    @Size(max = 8)
    @Column(name = "registered_date", length = 8)
    private String registeredDate;

    @Size(max = 400)
    @Column(name = "apartment_dong_name", length = 400)
    private String apartmentDongName;

    @Size(max = 100)
    @Column(name = "seller_gbn", length = 100)
    private String sellerGbn;

    @Size(max = 100)
    @Column(name = "buyer_gbn", length = 100)
    private String buyerGbn;

    @Size(max = 1)
    @Column(name = "land_leasehold_gbn", length = 1)
    private String landLeaseholdGbn;


    public static TransactionApartment from (Jibun jibun, ApartmentTransactionResponse.Item item) {
        TransactionApartment transactionApartment = TransactionApartment.builder()
                .jibun(jibun)
                .sggCode(item.sggCd())
                .emdCode(item.umdCd())
                .landCode(item.landCd())
                .bonbun(item.bonbun())
                .bubun(item.bubun())
                .roadName(item.roadNm())
                .roadNameSggCode(item.roadNmSggCd())
                .roadNameCode(item.roadNmCd())
                .roadNameSeq(item.roadNmSeq())
                .roadNamebCode(item.roadNmbCd())
                .roadNameBonbun(item.roadNmBonbun())
                .roadNameBubun(item.roadNmBubun())
                .legalDongName(item.umdNm())
                .apartmentName(item.aptNm())
                .jibunNumber(item.jibun())
                .exclusiveUseArea(StringUtils.isNotBlank(item.excluUseAr()) ? new BigDecimal(item.excluUseAr()) : null)
                .dealYear(Integer.parseInt(item.dealYear()))
                .dealMonth(Integer.parseInt(item.dealMonth()))
                .dealDay(Integer.parseInt(item.dealDay()))
                .dealAmount(item.dealAmount())
                .floor(Integer.parseInt(item.floor()))
                .builtYear(item.buildYear())
                .apartmentSeq(item.aptSeq())
                .cancelDealType(item.cdealType())
                .cancelDealDay(item.cdealDay())
                .dealGbn(item.dealingGbn())
                .estateAgentSggName(item.estateAgentSggNm())
                .registeredDate(item.rgstDate())
                .apartmentDongName(item.aptDong())
                .sellerGbn(item.slerGbn())
                .buyerGbn(item.buyerGbn())
                .landLeaseholdGbn(item.landLeaseholdGbn())
                .build();

        jibun.addEntity(transactionApartment);

        return transactionApartment;
    }

    public static TransactionApartment from (ApartmentTransactionResponse.Item item) {
        return from(null, item);
    }

    /**
     * 거래금액을 억 단위로 변환
     * @return
     */
    public BigDecimal getDealAmountInOneHundredMillion() {
        String dealAmountInNumber = this.dealAmount.replace(",", "");
        return new BigDecimal(dealAmountInNumber).divide(BigDecimal.valueOf(10_000));
    }

    /**
     * 전용면적을 평 단위로 변환
     * @return
     */
    public BigDecimal getExclusiveUseAreaInPyung() {
        return this.exclusiveUseArea.multiply(BigDecimal.valueOf(0.3025)).setScale(0, RoundingMode.FLOOR);
    }

    public String getDealYearMonth() {
        return this.dealYear.toString() + (this.dealMonth < 10 ? "0" + this.dealMonth : this.dealMonth);
    }
}