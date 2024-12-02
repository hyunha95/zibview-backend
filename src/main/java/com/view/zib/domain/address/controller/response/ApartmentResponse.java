package com.view.zib.domain.address.controller.response;

import com.view.zib.domain.address.entity.Jibun;
import com.view.zib.domain.address.entity.JibunDetail;
import com.view.zib.domain.transaction.entity.TransactionApartment;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Builder
public record ApartmentResponse (
        Long jibunId,
        String apartmentName,
        String jibunAddress,
        Integer builtYear, // 건축년도
        Integer yearsSinceConstruction, // 건축년수
        String roofName, // 지붕명
        String etcRoofName, // 건축물대장 지붕 정보
        Integer houseHoldCount, // 세대수
        Integer familyCount, // 가구수
        Integer hoCount, // 호수
        BigDecimal height, // 높이
        Integer groundFloorCount, // 지상층수
        Integer undergroundFloorCount, // 지하층수
        Integer elevatorCount, // 엘리베이터수
        Integer emergencyElevatorCount, // 비상용 엘리베이터수
        Integer indoorMechanicalParkingCount, // 옥내기계식대수
        Integer outdoorMechanicalParkingCount, // 옥외기계식대수
        Integer indoorSelfParkingCount, // 옥내자주식대수
        Integer outdoorSelfParkingCount, // 옥외자주식대수
        String structureName, // 구조명
        String etcStructureName, // 기타 구조명
        String earthquakeResistance, // 내진설계적용여부
        String earthquakeResistanceAbility, // 내진능력
        List<Pyung> pyungs
) {

    @Builder
    public record Pyung (
        Long transactionApartmentId,
        BigDecimal exclusiveUseArea,
        BigDecimal exclusiveUseAreaInPyung,
        BigDecimal dealAmountInOneHundredMillion,
        Integer floor

    ) {
        public static List<Pyung> from(List<TransactionApartment> transactionApartments) {
            return transactionApartments.stream()
                    .map(transactionApartment -> new Pyung(
                            transactionApartment.getId(),
                            transactionApartment.getExclusiveUseArea(),
                            transactionApartment.getExclusiveUseAreaInPyung(),
                            transactionApartment.getDealAmountInOneHundredMillion(),
                            transactionApartment.getFloor()
                    ))
                    .toList();
        }
    }

    /**
     * 정적 팩토리 메서드
     */
    public static ApartmentResponse from(Jibun jibun, List<TransactionApartment> foundTransactionApartments) {
        foundTransactionApartments.sort(Comparator.comparing(TransactionApartment::getExclusiveUseArea));
        List<Pyung> pyungs = Pyung.from(foundTransactionApartments);
        Optional<JibunDetail> jibunDetail = Optional.ofNullable(jibun.getJibunDetail());

        Integer builtYear = jibunDetail.map(JibunDetail::getBuiltYear).orElse(null);
        Integer yearsSinceConstruction = null;
        if (builtYear != null) {
            yearsSinceConstruction = LocalDate.now().minusYears(builtYear).getYear();
        }

        return ApartmentResponse.builder()
                .jibunId(jibun.getId())
                .apartmentName(jibunDetail.map(JibunDetail::getApartmentName).orElse(null))
                .jibunAddress(jibun.getJibunAddress())
                .builtYear(builtYear)
                .yearsSinceConstruction(yearsSinceConstruction)
                .roofName(jibunDetail.map(JibunDetail::getRoofCodeName).orElse(null))
                .etcRoofName(jibunDetail.map(JibunDetail::getEtcRoofName).orElse(null))
                .houseHoldCount(jibunDetail.map(JibunDetail::getHouseHoldCount).orElse(null))
                .familyCount(jibunDetail.map(JibunDetail::getFamilyCount).orElse(null))
                .hoCount(jibunDetail.map(JibunDetail::getHoCount).orElse(null))
                .height(jibunDetail.map(JibunDetail::getHeight).orElse(null))
                .groundFloorCount(jibunDetail.map(JibunDetail::getGroundFloorCount).orElse(null))
                .undergroundFloorCount(jibunDetail.map(JibunDetail::getUndergroundFloorCount).orElse(null))
                .elevatorCount(jibunDetail.map(JibunDetail::getElevatorCount).orElse(null))
                .emergencyElevatorCount(jibunDetail.map(JibunDetail::getEmergencyElevatorCount).orElse(null))
                .indoorMechanicalParkingCount(jibunDetail.map(JibunDetail::getIndoorMechanicalParkingCount).orElse(null))
                .outdoorMechanicalParkingCount(jibunDetail.map(JibunDetail::getOutdoorMechanicalParkingCount).orElse(null))
                .indoorSelfParkingCount(jibunDetail.map(JibunDetail::getIndoorSelfParkingCount).orElse(null))
                .outdoorSelfParkingCount(jibunDetail.map(JibunDetail::getOutdoorSelfParkingCount).orElse(null))
                .structureName(jibunDetail.map(JibunDetail::getStructureCodeName).orElse(null))
                .etcStructureName(jibunDetail.map(JibunDetail::getEtcStructure).orElse(null))
                .builtYear(jibunDetail.map(JibunDetail::getBuiltYear).orElse(null))
                .earthquakeResistance("")
                .earthquakeResistanceAbility("")
                .pyungs(pyungs)
                .build();
    }
}
