package com.view.zib.domain.address.controller.response;

import com.view.zib.domain.transaction.entity.TransactionApartment;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Builder
public record TransactionApartmentResponse (
        Long transactionApartmentId,
        String dealDate,
        BigDecimal exclusiveUseArea,
        BigDecimal exclusiveUseAreaInPyung,
        LocalDate dealYMD,
        int dealYear,
        int dealMonth,
        int dealDay,
        BigDecimal dealAmountInOneHundredMillion,
        int floor,
        String builtYear,
        String dealGbn,
        String estateAgentSggName,
        String apartmentDongName,
        String sellerGbn,
        String buyerGbn
) {

    public static TransactionApartmentResponse from(TransactionApartment ta) {
        String dealDate = LocalDate.of(ta.getDealYear(), ta.getDealMonth(), ta.getDealDay()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return TransactionApartmentResponse.builder()
                .transactionApartmentId(ta.getId())
                .dealDate(dealDate)
                .exclusiveUseArea(ta.getExclusiveUseArea())
                .exclusiveUseAreaInPyung(ta.getExclusiveUseAreaInPyung())
                .dealYear(ta.getDealYear())
                .dealMonth(ta.getDealMonth())
                .dealDay(ta.getDealDay())
                .dealAmountInOneHundredMillion(ta.getDealAmountInOneHundredMillion())
                .floor(ta.getFloor())
                .builtYear(ta.getBuiltYear())
                .dealGbn(ta.getDealGbn())
                .estateAgentSggName(ta.getEstateAgentSggName())
                .apartmentDongName(ta.getApartmentDongName())
                .sellerGbn(ta.getSellerGbn())
                .buyerGbn(ta.getBuyerGbn())
                .build();
    }

    public static List<TransactionApartmentResponse> from(List<TransactionApartment> transactionApartments) {
        return transactionApartments.stream()
                .map(TransactionApartmentResponse::from)
                .toList();
    }
}
