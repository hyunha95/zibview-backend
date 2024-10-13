package com.view.zib.domain.address.repository.dto;

import com.view.zib.domain.address.entity.Jibun;
import com.view.zib.domain.client.vworld.dto.ApartmentTransactionResponse;
import io.micrometer.common.util.StringUtils;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TransactionApartmentDTO(
        Long jibunId,
        String sggCode,
        String emdCode,
        String landCode,
        String bonbun,
        String bubun,
        String roadName,
        String roadNameSggCode,
        String roadNameCode,
        String roadNameSeq,
        String roadNamebCode,
        String roadNameBonbun,
        String roadNameBubun,
        String legalDongName,
        String apartmentName,
        String jibunNumber,
        BigDecimal exclusiveUseArea,
        Integer dealYear,
        Integer dealMonth,
        Integer dealDay,
        String dealAmount,
        Integer floor,
        String builtYear,
        String apartmentSeq,
        String cancelDealType,
        String cancelDealDay,
        String dealGbn,
        String estateAgentSggName,
        String registeredDate,
        String apartmentDongName,
        String sellerGbn,
        String buyerGbn,
        String landLeaseholdGbn
) {

    public static TransactionApartmentDTO from(Jibun jibun, ApartmentTransactionResponse.Item item) {
        return TransactionApartmentDTO.builder()
                .jibunId(jibun.getId())
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
    }
}
