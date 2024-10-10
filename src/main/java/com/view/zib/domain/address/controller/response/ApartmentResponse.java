package com.view.zib.domain.address.controller.response;

import com.view.zib.domain.address.entity.Jibun;
import com.view.zib.domain.address.entity.JibunDetail;
import com.view.zib.domain.transaction.entity.TransactionApartment;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Builder
public record ApartmentResponse (
        Long jibunId,
        String apartmentName,
        String jibunAddress,
        int builtYear, // 건축년도
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
//        List<TransactionApartmentDTO> transactionApartments
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

    public static ApartmentResponse from(Jibun jibun, List<TransactionApartment> foundTransactionApartments) {
//        List<TransactionApartmentDTO> transactionApartmentDTOS = TransactionApartmentDTO.from(jibun.getTransactionApartments());
        List<Pyung> pyungs = Pyung.from(foundTransactionApartments);
        Optional<JibunDetail> jibunDetail = Optional.ofNullable(jibun.getJibunDetail());

        return ApartmentResponse.builder()
                .jibunId(jibun.getId())
                .apartmentName("")
                .jibunAddress(jibun.getJibunAddress())
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
                .earthquakeResistance("")
                .earthquakeResistanceAbility("")
                .pyungs(pyungs)
//                .transactionApartments(transactionApartmentDTOS)
                .build();
    }

    @Builder
    public record TransactionApartmentDTO (
            Long transactionApartmentId,
            String roadName,
            String legalDongName,
            String apartmentName,
            String jibunNumber,
            BigDecimal exclusiveUseArea,
            BigDecimal exclusiveUseAreaInPyung,
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
        public static TransactionApartmentDTO from(TransactionApartment transactionApartment) {
            return TransactionApartmentDTO.builder()
                    .transactionApartmentId(transactionApartment.getId())
                    .roadName(transactionApartment.getRoadName())
                    .legalDongName(transactionApartment.getLegalDongName())
                    .apartmentName(transactionApartment.getApartmentName())
                    .jibunNumber(transactionApartment.getJibunNumber())
                    .exclusiveUseArea(transactionApartment.getExclusiveUseArea())
                    .exclusiveUseAreaInPyung(transactionApartment.getExclusiveUseAreaInPyung())
                    .dealYear(Integer.parseInt(transactionApartment.getDealYear()))
                    .dealMonth(Integer.parseInt(transactionApartment.getDealMonth()))
                    .dealDay(Integer.parseInt(transactionApartment.getDealDay()))
                    .dealAmountInOneHundredMillion(transactionApartment.getDealAmountInOneHundredMillion())
                    .floor(transactionApartment.getFloor())
                    .builtYear(transactionApartment.getBuiltYear())
                    .dealGbn(transactionApartment.getDealGbn())
                    .estateAgentSggName(transactionApartment.getEstateAgentSggName())
                    .apartmentDongName(transactionApartment.getApartmentDongName())
                    .sellerGbn(transactionApartment.getSellerGbn())
                    .buyerGbn(transactionApartment.getBuyerGbn())
                    .build();
        }

        public static List<TransactionApartmentDTO> from(List<TransactionApartment> transactionApartments) {
            return transactionApartments.stream()
                    .map(TransactionApartmentDTO::from)
                    .toList();
        }
    }
}
