package com.view.zib.domain.client.vworld.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.List;

public record OfficeTelTransactionClientDTO(
        Header header,
        Body body
) {

    public record Header(String resultCode, String resultMsg) {}
    public record Body(Items items, int numOfRows, int pageNo, int totalCount) {}
    public record Items(
            @JacksonXmlElementWrapper(useWrapping = false)
            List<Item> item
    ) {}

    public record Item(
            String sggCd,
            String sggNm,
            String umdNm,
            String jibun,
            String offiNm,
            String excluUseAr,
            String dealYear,
            String dealMonth,
            String dealDay,
            String deposit,
            String monthlyRent,
            String floor,
            String buildYear,
            String contractTerm,
            String contractType,
            String useRRRight,
            String preDeposit,
            String preMonthlyRent
    ) {}
}
