package com.view.zib.domain.client.vworld.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record VWorldResponseDto(Response response) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Response(Header header, Body body) {}
    public record Header(String resultCode, String resultMsg) {}
    public record Body(Items items) {}
    public record Items(Item item) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Item(
            @JsonProperty("mainPurpsCdNm")
            String mainPurposeCodeName,
            @JsonProperty("mainPurpsCd")
            String mainPurposeCode,
            @JsonProperty("etcPurps")
            String etcPurposeName,
            @JsonProperty("roofCd")
            String roofCode,
            @JsonProperty("roofCdNm")
            String rootCodeName,
            @JsonProperty("etcRoof")
            String etcRootName,
            @JsonProperty("hhldCnt")
            Integer houseHoldCount,
            @JsonProperty("fmlyCnt")
            Integer familyCount,
            @JsonProperty("heit")
            BigDecimal height,
            @JsonProperty("grndFlrCnt")
            Integer groundFloorCount,
            @JsonProperty("ugrndFlrCnt")
            Integer undergroundFloorCount,
            @JsonProperty("rideUseElvtCnt")
            Integer elevatorCount,
            @JsonProperty("emgenUseElvtCnt")
            Integer emergencyElevatorCount,
            @JsonProperty("indrMechUtcnt")
            Integer indoorMechanicalParkingCount,
            @JsonProperty("oudrMechUtcnt")
            Integer outdoorMechanicalParkingCount,
            @JsonProperty("indrAutoUtcnt")
            Integer indoorSelfParkingCount,
            @JsonProperty("oudrAutoUtcnt")
            Integer outdoorSelfParkingCount,
            @JsonProperty("hoCnt")
            Integer hoCount,
            @JsonProperty("strctCd")
            Character structureCode,
            @JsonProperty("strctCdNm")
            String structureCodeName,
            @JsonProperty("etcStrct")
            String etcStructure,
            @JsonProperty("rserthqkDsgnApplyYn")
            Boolean earthquakeResistant,
            @JsonProperty("rserthqkAblty")
            String earthquakeResistantAbility
    ) {
    }

}


