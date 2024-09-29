package com.view.zib.domain.client.vworld.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.List;

public record ApartmentTransactionResponse(
        ApartmentTransactionResponse.Header header,
        ApartmentTransactionResponse.Body body
) {

    public record Header(String resultCode, String resultMsg) {}
    public record Body(ApartmentTransactionResponse.Items items, int numOfRows, int pageNo, int totalCount) {}

    public record Items(
            @JacksonXmlElementWrapper(useWrapping = false)
            List<ApartmentTransactionResponse.Item> item
    ) {}

    public record Item(
            String sggCd,
            String umdCd,
            String landCd,
            String bonbun,
            String bubun,
            String roadNm,
            String roadNmSggCd,
            String roadNmCd,
            String roadNmSeq,
            String roadNmbCd,
            String roadNmBonbun,
            String roadNmBubun,
            String umdNm,
            String aptNm,
            String jibun,
            String excluUseAr,
            String dealYear,
            String dealMonth,
            String dealDay,
            String dealAmount,
            String floor,
            String buildYear,
            String aptSeq,
            String cdealType,
            String cdealDay,
            String dealingGbn,
            String estateAgentSggNm,
            String rgstDate,
            String aptDong,
            String slerGbn,
            String buyerGbn,
            String landLeaseholdGbn
    ) {

        public String legalDongCode() {
            return sggCd + umdCd;
        }
    }
}
