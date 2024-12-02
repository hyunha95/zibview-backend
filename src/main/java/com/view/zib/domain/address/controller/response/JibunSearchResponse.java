package com.view.zib.domain.address.controller.response;

import com.view.zib.domain.address.repository.dto.JibunSearchResultDTO;
import com.view.zib.domain.transaction.entity.TransactionApartment;
import com.view.zib.domain.transaction.hash.TransactionApartmentHash;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Builder
public record JibunSearchResponse(
        Long jibunId,
        String jibunAddress,
        String sidoName,
        String sggName,
        String emdName,
        String riName,
        String mountainYn,
        Integer jibunMain,
        Integer jibunSub,
        String buildingName,
        String legalDongCode,
        BigDecimal xCoordinate,
        BigDecimal yCoordinate,
        BigDecimal exclusiveUseArea,
        BigDecimal dealAmount

) {


    public static JibunSearchResponse from(JibunSearchResultDTO jibunSearchResultDTO, TransactionApartmentHash transactionApartment) {

        return JibunSearchResponse.builder()
                .jibunId(jibunSearchResultDTO.getJibunId())
                .jibunAddress(jibunSearchResultDTO.getJibunAddress())
                .sidoName(jibunSearchResultDTO.getSidoName())
                .sggName(jibunSearchResultDTO.getSggName())
                .emdName(jibunSearchResultDTO.getEmdName())
                .riName(jibunSearchResultDTO.getRiName())
                .mountainYn(jibunSearchResultDTO.getMountainYn())
                .jibunMain(jibunSearchResultDTO.getJibunMain())
                .jibunSub(jibunSearchResultDTO.getJibunSub())
                .buildingName(jibunSearchResultDTO.getBuildingName())
                .legalDongCode(jibunSearchResultDTO.getLegalDongCode())
                .xCoordinate(jibunSearchResultDTO.getXCoordinate())
                .yCoordinate(jibunSearchResultDTO.getYCoordinate())
                .exclusiveUseArea(transactionApartment.getExclusiveUseAreaInPyung())
                .dealAmount(transactionApartment.getDealAmountInOneHundredMillion())
                .build();
    }

    public static List<JibunSearchResponse> of(List<JibunSearchResultDTO> jibunSearchResultDTOS, List<TransactionApartmentHash> transactionApartments) {
        Map<Long, TransactionApartmentHash> transactionApartmentsByJibunId = transactionApartments.stream()
                .collect(Collectors.toMap(
                        TransactionApartmentHash::getJibunId,
                        Function.identity(),
                        (a, b) -> a)
                );

        return jibunSearchResultDTOS.stream()
                .filter(dto -> transactionApartmentsByJibunId.containsKey(dto.getJibunId()))
                .map(dto -> from(dto, transactionApartmentsByJibunId.get(dto.getJibunId())))
                .toList();
    }
}
