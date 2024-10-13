package com.view.zib.domain.address.controller.response;

import com.view.zib.domain.transaction.entity.TransactionApartment;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
public record TransactionApartmentResponse (
        Long transactionApartmentId,
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
        LocalDate.of(ta.getDealYear(), ta.getDealMonth(), ta.getDealDay());

        return TransactionApartmentResponse.builder()
                .transactionApartmentId(ta.getId())
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
