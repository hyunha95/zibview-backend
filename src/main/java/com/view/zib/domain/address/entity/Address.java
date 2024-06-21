package com.view.zib.domain.address.entity;

import com.view.zib.domain.api.kako.domain.Document;
import com.view.zib.domain.api.kako.domain.KakaoAddressResponse;
import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.post.controller.request.PostRequest;
import com.view.zib.domain.post.entity.Post;
import com.view.zib.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 프론트에서 카카오 지도를 호출해서 리턴되는 값
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Address extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder.Default
    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<Image> images = new ArrayList<>();

    private String zonecode;           // 	13529	국가기초구역번호. 2015년 8월 1일부터 시행될 새 우편번호.
    private String address;            // 	경기 성남시 분당구 판교역로 166	기본 주소 (검색 결과에서 첫줄에 나오는 주소, 검색어의 타입(지번/도로명)에 따라 달라집니다.)
    private String addressEnglish;     // 	166, Pangyoyeok-ro, Bundang-gu, Seongnam-si, Gyeonggi-do, korea	기본 영문 주소
    private String addressType;        // 	R/J	검색된 기본 주소 타입: R(도로명), J(지번)
    private String userSelectedType;   // 	R/J	검색 결과에서 사용자가 선택한 주소의 타입
    private String noSelected;         // 	Y/N	연관 주소에서 "선택 안함" 부분을 선택했을때를 구분할 수 있는 상태변수
    private String userLanguageType;   // 	K/E	검색 결과에서 사용자가 선택한 주소의 언어 타입: K(한글주소), E(영문주소)
    private String roadAddress;        // 	경기 성남시 분당구 판교역로 166	도로명 주소 (지번:도로명 주소가 1:N인 경우에는 데이터가 공백으로 들어갈 수 있습니다. - 아래 autoRoadAddress의 자세한 설명 참고)
    private String roadAddressEnglish; // 	166, Pangyoyeok-ro, Bundang-gu, Seongnam-si, Gyeonggi-do, Korea	영문 도로명 주소
    private String jibunAddress;       // 	경기 성남시 분당구 백현동 532	지번 주소 (도로명:지번 주소가 1:N인 경우에는 데이터가 공백으로 들어갈 수 있습니다. - 아래 autoJibunAddress의 자세한 설명 참고)
    private String jibunAddressEnglish; // 	532, Baekhyeon-dong, Bundang-gu, Seongnam-si, Gyeonggi-do, Korea	영문 지번 주소
    private String autoRoadAddress;    // 	경기 성남시 분당구 판교역로 166	'지번주소'에 매핑된 '도로명주소'가 여러개인 경우, 사용자가 '선택안함' 또는 '지번주소'를 클릭했을 때 연관된 도로명 주소 중 임의로 첫번째 매핑 주소를 넣어서 반환합니다. (autoMapping을 false로 설정한 경우에는 값이 채워지지 않습니다.)
    private String autoRoadAddressEnglish; // 	166, Pangyoyeok-ro, Bundang-gu, Seongnam-si, Gyeonggi-do, Korea	autoRoadAddress의 영문 도로명 주소
    private String autoJibunAddress;   // 	경기 성남시 분당구 백현동 532	'도로명주소'에 매핑된 '지번주소'가 여러개인 경우, 사용자가 '선택안함' 또는 '도로명주소'를 클릭했을 때 연관된 지번 주소 중 임의로 첫번째 매핑 주소를 넣어서 반환합니다. (autoMapping을 false로 설정한 경우에는 값이 채워지지 않습니다.)
    private String autoJibunAddressEnglish; // 	532, Baekhyeon-dong, Bundang-gu, Seongnam-si, Gyeonggi-do, Korea	autoJibunAddress의 영문 지번 주소

    @Column(unique = true) //  TODO: 주소 검색 시 해당 주소로 등록된 건물이 있는 확인 필요
    private String buildingCode;       // 	4113511000105320000000002	건물관리번호
    private String buildingName;       // 	카카오 판교 아지트	건물명
    private String apartment;          // 	Y/N	공동주택 여부 (아파트,연립주택,다세대주택 등)
    private String sido;               // 	경기	도/시 이름
    private String sidoEnglish;        // 	Gyeonggi-do	도/시 이름의 영문
    private String sigungu;            // 	성남시 분당구	시/군/구 이름
    private String sigunguEnglish;     // 	Bundang-gu Seongnam-si	시/군/구 이름의 영문
    private String sigunguCode;        // 	41135	시/군/구 코드 (5자리 구성된 시/군/구 코드입니다.)
    private String roadnameCode;       // 	3179025	도로명 코드, 7자리로 구성된 도로명 코드입니다. 추후 7자리 이상으로 늘어날 수 있습니다.
    private String bcode;              // 	4113511000	법정동/법정리 코드
    private String roadname;           // 	판교역로	도로명 값, 검색 결과 중 선택한 도로명주소의 "도로명" 값이 들어갑니다.(건물번호 제외)
    private String roadnameEnglish;    // 	Pangyoyeok-ro	도로명 값, 검색 결과 중 선택한 도로명주소의 "도로명의 영문" 값이 들어갑니다.(건물번호 제외)
    private String bname;              // 	백현동	법정동/법정리 이름
    private String bnameEnglish;       // 	Baekhyeon-dong	법정동/법정리 이름의 영문
    private String bname1;             // 		법정리의 읍/면 이름 ("동"지역일 경우에는 공백, "리"지역일 경우에는 "읍" 또는 "면" 정보가 들어갑니다.)
    private String bname1English;      // 		법정리의 읍/면 이름의 영문 ("동"지역일 경우에는 공백, "리"지역일 경우에는 "읍" 또는 "면" 정보가 들어갑니다.)
    private String bname2;             // 	백현동	법정동/법정리 이름
    private String bname2English;      // 	Baekhyeon-dong	법정동/법정리 이름의 영문
    private String hname;              // 	-	행정동 이름, 검색어를 행정동으로 검색하고, 검색결과의 법정동과 검색어에 입력한 행정동과 다른 경우에 표시하고, 데이터를 내립니다.
    private String query;              // 	판교역로 166	사용자가 입력한 검색어

    private double longitude; // X 좌표값, 경위도인 경우 경도(longitude)
    private double latitude;  // Y 좌표값, 경위도인 경우 위도(latitude)

    public static Address of(PostRequest.Address addressRequest, List<Image> images, KakaoAddressResponse kakaoAddressResponse) {
        Address address = Address.builder()
                .images(images)
                .zonecode(addressRequest.getZonecode())
                .address(addressRequest.getAddress())
                .addressEnglish(addressRequest.getAddressEnglish())
                .addressType(addressRequest.getAddressType())
                .userSelectedType(addressRequest.getUserSelectedType())
                .noSelected(addressRequest.getNoSelected())
                .userLanguageType(addressRequest.getUserLanguageType())
                .roadAddress(addressRequest.getRoadAddress())
                .roadAddressEnglish(addressRequest.getRoadAddressEnglish())
                .jibunAddress(addressRequest.getJibunAddress())
                .jibunAddressEnglish(addressRequest.getJibunAddressEnglish())
                .autoRoadAddress(addressRequest.getAutoRoadAddress())
                .autoRoadAddressEnglish(addressRequest.getAutoRoadAddressEnglish())
                .autoJibunAddress(addressRequest.getAutoJibunAddress())
                .autoJibunAddressEnglish(addressRequest.getAutoJibunAddressEnglish())
                .buildingCode(addressRequest.getBuildingCode())
                .buildingName(addressRequest.getBuildingName())
                .apartment(addressRequest.getApartment())
                .sido(addressRequest.getSido())
                .sidoEnglish(addressRequest.getSidoEnglish())
                .sigungu(addressRequest.getSigungu())
                .sigunguEnglish(addressRequest.getSigunguEnglish())
                .sigunguCode(addressRequest.getSigunguCode())
                .roadnameCode(addressRequest.getRoadnameCode())
                .bcode(addressRequest.getBcode())
                .roadname(addressRequest.getRoadname())
                .roadnameEnglish(addressRequest.getRoadnameEnglish())
                .bname(addressRequest.getBname())
                .bnameEnglish(addressRequest.getBnameEnglish())
                .bname1(addressRequest.getBname1())
                .bname1English(addressRequest.getBname1English())
                .bname2(addressRequest.getBname2())
                .bname2English(addressRequest.getBname2English())
                .hname(addressRequest.getHname())
                .query(addressRequest.getQuery())
                .build();

        if (!kakaoAddressResponse.getDocuments().isEmpty()) {
            Document document = kakaoAddressResponse.getDocuments().getFirst();
            double longitude = document.getX();
            double latitude = document.getY();
            address.updateCoordinate(longitude, latitude);
        }

        images.forEach(image -> image.addEntity(address));

        return address;
    }

    public void updateCoordinate(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public void addEntity(Post post) {
        this.post = post;
    }
}
