package com.view.zib.domain.building.enums;

import lombok.Getter;

@Getter
public enum BuildingType {

    APARTMENT("아파트"), OFFICETEL("오피스텔"), HOUSE("주택"), VILLA("빌라");

    private final String buildingTypeName;

    BuildingType(String buildingTypeName) {
        this.buildingTypeName = buildingTypeName;
    }
}
