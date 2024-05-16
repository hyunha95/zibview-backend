package com.view.zib.domain.api.building.controller.response;

import com.view.zib.domain.api.building.domain.BuildingUseApiResponse;
import lombok.Builder;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.toList;

/**
 * BuildingUseResponse
 * @param detailPurposes
 */
public record BuildingUseResponse(List<DetailPurpose> detailPurposes) {

    @Builder
    public BuildingUseResponse {
    }

    /**
     * @param apiResponse
     * @return BuildingUseResponse
     */
    public static BuildingUseResponse from(BuildingUseApiResponse apiResponse) {
        List<BuildingUseApiResponse.Field> fields = apiResponse.getBuildingUses().getField();

        Map<DetailPurposeKey, List<BuildingUseApiResponse.Field>> fieldsByDetailPurposeCode = fields.stream()
                .collect(groupingBy(DetailPurposeKey::new, toList()));

        List<DetailPurpose> detailPurposes = fieldsByDetailPurposeCode.keySet().stream()
                .map(detailPurposeKey -> new DetailPurpose(detailPurposeKey, fieldsByDetailPurposeCode.get(detailPurposeKey)))
                .toList();

        return BuildingUseResponse.builder()
                .detailPurposes(detailPurposes)
                .build();
    }

    /**
     * DetailPurposeKey
     */
    public record DetailPurposeKey(String mainPurposeCodeName) {
        public DetailPurposeKey(BuildingUseApiResponse.Field field) {
            this(field.getMainPrposCodeNm());
        }
    }

    /**
     * DetailPurpose
     * @param buildings
     */
    public record DetailPurpose(String mainPurposeCodeName, List<Building> buildings) {
        public DetailPurpose(DetailPurposeKey detailPurposeKey, List<BuildingUseApiResponse.Field> fields) {
            this(
                    detailPurposeKey.mainPurposeCodeName(),
                    fields.stream().collect(groupingBy(BuildingUseApiResponse.Field::getBldNm, toList()))
                            .entrySet().stream()
                            .map(entry -> new Building(entry.getValue(), entry.getKey()))
                            .toList()
            );
        }
    }

    /**
     * Building
     * @param buildingName
     * @param buildingDongList
     */
    public record Building(String buildingName, List<BuildingDong> buildingDongList) {
        public Building(List<BuildingUseApiResponse.Field> fields, String buildingName) {
            this(
                    buildingName,
                    fields.stream().map(field -> new BuildingDong(field.getDongNm(), field.getGrndFlrCnt(), field.getUgrndFlrCnt())).toList()
            );
        }
    }

    /**
     * BuildingDong
     * @param buildingDongName
     * @param floor
     */
    public record BuildingDong(String buildingDongName, Floor floor) {
        public BuildingDong(String buildingDongName, int groundFloors, int undergroundFloors) {
            this(
                    buildingDongName,
                    new Floor(groundFloors, undergroundFloors)
            );
        }
    }

    /**
     * Floor
     * @param groundFloors
     * @param undergroundFloors
     */
    public record Floor(List<Integer> groundFloors, List<Integer> undergroundFloors) {
        public Floor(int groundFloors, int undergroundFloors) {
            this(
                    IntStream.rangeClosed(1, groundFloors).boxed().toList(),
                    IntStream.rangeClosed(1, undergroundFloors).boxed().toList()
            );
        }
    }
}
