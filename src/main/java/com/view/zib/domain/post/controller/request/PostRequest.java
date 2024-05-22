package com.view.zib.domain.post.controller.request;

import com.view.zib.domain.building.enums.BuildingType;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostRequest {
    private String title;
    private String description;
    private List<String> imageUuids;
    private BuildingType buildingType;
    private Address address;
    private ContractInfo contractInfo;


    @NoArgsConstructor
    @Getter
    public static class Save extends PostRequest {

        @Builder
        public Save(String title, String description, List<String> imageUuids, BuildingType buildingType, Address address, ContractInfo contractInfo) {
            super(title, description, imageUuids, buildingType, address, contractInfo);
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Address {
        private String address; // 서울 서초구 강남대로 27
        private String addressEnglish; // 27, Gangnam-daero, Seocho-gu, Seoul, Korea
        private String addressType; // R
        private String apartment; // N
        private String autoJibunAddress; //
        private String autoJibunAddressEnglish; //
        private String autoRoadAddress; //
        private String autoRoadAddressEnglish; //
        private String bcode; // 1165010200
        private String bname; // 양재동
        private String bname1; //
        private String bname1English; //
        private String bname2; // 양재동
        private String bname2English; // Yangjae-dong
        private String bnameEnglish; // Yangjae-dong
        private String buildingCode; // 1165010200102320000000001
        private String buildingName; // AT센터
        private String detailAddress; //
        private String hname; //
        private String jibunAddress; // 서울 서초구 양재동 232
        private String jibunAddressEnglish; // 232, Yangjae-dong, Seocho-gu, Seoul, Korea
        private String noSelected; // N
        private String postcode; //
        private String postcode1; //
        private String postcode2; //
        private String postcodeSeq; //
        private String query; // 서초
        private String roadAddress; // 서울 서초구 강남대로 27
        private String roadAddressEnglish; // 27, Gangnam-daero, Seocho-gu, Seoul, Korea
        private String roadname; // 강남대로
        private String roadnameCode; // 2102001
        private String roadnameEnglish; // Gangnam-daero
        private String sido; // 서울
        private String sidoEnglish; // Seoul
        private String sigungu; // 서초구
        private String sigunguCode; // 11650
        private String sigunguEnglish; // Seocho-gu
        private String userLanguageType; // K
        private String userSelectedType; // R
        private String zonecode; // 0677
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ContractInfo {
        private LocalDate contractStartDate;
        private LocalDate contractEndDate;
        private double deposit;        // 보증금
        private double monthlyFee;        // 월세
        private double maintenanceFee;     // 관리비
    }
}
