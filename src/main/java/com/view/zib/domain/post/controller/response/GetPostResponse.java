package com.view.zib.domain.post.controller.response;

import com.view.zib.domain.address.entity.*;
import com.view.zib.domain.client.kako.domain.Coordinate;
import com.view.zib.domain.client.vworld.dto.VWorldResponseDto;
import com.view.zib.domain.post.entity.Post;
import com.view.zib.domain.post.entity.SubPost;
import com.view.zib.domain.post.entity.SubPostLike;
import com.view.zib.domain.storage.service.StorageService;
import lombok.Builder;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.view.zib.domain.post.controller.response.SubPostDTO.mapToSubPostDtos;

public record GetPostResponse(
        BigDecimal latitude,
        BigDecimal longitude,
        String buildingName,
        String sigunguBuildingName,
        String roadNameAddress,
        String sigungu,
        String emd,
        List<JibunDTO> jibuns,
        List<SubPostDTO> subPosts
) {

    @Builder
    public record JibunDTO(
            String mainPurposeName,
            String etcPurposeName,
            Integer houseHoldCount,// 세대수
            Integer indoorMechanicalParkingCount, // 옥내기계식대수
            Integer outdoorMechanicalParkingCount, // 옥외기계식대수
            Integer indoorSelfParkingCount, // 옥내자주식대수
            Integer outdoorSelfParkingCount, // 옥외자주식대수
            Integer hoCount, // 호수
            Integer groundFloorCount, // 지상층수
            Integer undergroundFloorCount, // 지하층수
            Integer elevatorCount, // 엘리베이터 수
            Integer emergencyElevatorCount // 비상용 엘리베이터 수
    ) {
        public static List<JibunDTO> from(List<Jibun> jibuns) {
            return jibuns.stream()
                    .map(Jibun::getJibunDetail)
                    .filter(Objects::nonNull)
                    .map(jibunDetail -> JibunDTO.builder()
                            .mainPurposeName(jibunDetail.getMainPurposeCodeName())
                            .etcPurposeName(jibunDetail.getEtcPurposeName())
                            .houseHoldCount(jibunDetail.getHouseHoldCount())
                            .indoorMechanicalParkingCount(jibunDetail.getIndoorMechanicalParkingCount())
                            .outdoorMechanicalParkingCount(jibunDetail.getOutdoorMechanicalParkingCount())
                            .indoorSelfParkingCount(jibunDetail.getIndoorSelfParkingCount())
                            .outdoorSelfParkingCount(jibunDetail.getOutdoorSelfParkingCount())
                            .hoCount(jibunDetail.getHoCount())
                            .groundFloorCount(jibunDetail.getGroundFloorCount())
                            .undergroundFloorCount(jibunDetail.getUndergroundFloorCount())
                            .elevatorCount(jibunDetail.getElevatorCount())
                            .emergencyElevatorCount(jibunDetail.getEmergencyElevatorCount())
                            .build())
                    .toList();
        }

        public static JibunDTO from(VWorldResponseDto.Item item) {
            return JibunDTO.builder()
                    .mainPurposeName(item.mainPurposeCodeName())
                    .etcPurposeName(item.etcPurposeName())
                    .houseHoldCount(item.houseHoldCount())
                    .indoorMechanicalParkingCount(item.indoorMechanicalParkingCount())
                    .outdoorMechanicalParkingCount(item.outdoorMechanicalParkingCount())
                    .indoorSelfParkingCount(item.indoorSelfParkingCount())
                    .outdoorSelfParkingCount(item.outdoorSelfParkingCount())
                    .hoCount(item.hoCount())
                    .groundFloorCount(item.groundFloorCount())
                    .undergroundFloorCount(item.undergroundFloorCount())
                    .elevatorCount(item.elevatorCount())
                    .emergencyElevatorCount(item.emergencyElevatorCount())
                    .build();
        }
    }

    /**
     * 정적 팩토리 메서드
     */
    public static GetPostResponse of(Post post, List<SubPost> subPosts, List<SubPostLike> subPostLikes, StorageService storageService) {
        RoadNameAddress roadNameAddress = post.getRoadNameAddress();
        List<Jibun> jibuns = roadNameAddress.getJibuns();
        AddressAdditionalInfo addressAdditionalInfo = roadNameAddress.getAddressAdditionalInfo();
        RoadNameCode roadNameCode = roadNameAddress.getRoadNameCode();

        List<SubPostDTO> subPostDtos = subPosts.stream()
                .map(mapToSubPostDtos(subPostLikes, storageService))
                .toList();


        if (CollectionUtils.isEmpty(jibuns)) {
            return new GetPostResponse(
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    addressAdditionalInfo.getBuildingName(),
                    addressAdditionalInfo.getSggBuildingName(),
                    roadNameAddress.getShortRoadNameAddress(),
                    roadNameCode.getSggName(),
                    roadNameCode.getEmdName(),
                    Collections.emptyList(),
                    subPostDtos
            );
        }

        return new GetPostResponse(
                Optional.ofNullable(roadNameAddress.getLatitude()).orElse(BigDecimal.ZERO),
                Optional.ofNullable(roadNameAddress.getLongitude()).orElse(BigDecimal.ZERO),
                addressAdditionalInfo.getBuildingName(),
                addressAdditionalInfo.getSggBuildingName(),
                roadNameAddress.getShortRoadNameAddress(),
                roadNameCode.getSggName(),
                roadNameCode.getEmdName(),
                JibunDTO.from(jibuns),
                subPostDtos
        );
    }


    public boolean hasCoordinate() {
        return BigDecimal.ZERO.compareTo(latitude) < 0 && BigDecimal.ZERO.compareTo(longitude) < 0;
    }


    public GetPostResponse setNewCoordinate(Coordinate coordinate) {
        return new GetPostResponse(
                coordinate.latitude(),
                coordinate.longitude(),
                buildingName,
                sigunguBuildingName,
                roadNameAddress,
                sigungu,
                emd,
                jibuns,
                subPosts
        );
    }

    public GetPostResponse updateJibunDetails(List<VWorldResponseDto> responseDtos) {
        List<JibunDTO> jibunDtos = responseDtos.stream()
                .map(VWorldResponseDto::response)
                .map(VWorldResponseDto.Response::body)
                .map(VWorldResponseDto.Body::items)
                .map(VWorldResponseDto.Items::item)
                .map(JibunDTO::from)
                .toList();

        return new GetPostResponse(
                this.latitude,
                this.longitude,
                this.buildingName,
                this.sigunguBuildingName,
                this.roadNameAddress,
                this.sigungu,
                this.emd,
                jibunDtos,
                this.subPosts
        );
    }
}
