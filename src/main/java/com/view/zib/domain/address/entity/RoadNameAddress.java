package com.view.zib.domain.address.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "road_name_address")
public class RoadNameAddress {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "road_name_address_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "road_name_code_id")
    private RoadNameCode roadNameCode;

    @OneToOne
    @JoinColumn(name = "additional_info_id")
    private AdditionalInfo additionalInfo;

    // 관리번호
    @Column(name = "management_num", length = 25)
    private String managementNum;

    // 지하여부 (0:지상, 1:지하)
    @Column(name = "below_ground", length = 1)
    private String belowGround;

    // 건물본번
    @Column(name = "building_num", length = 5)
    private String buildingNum;

    // 건물부번
    @Column(name = "building_sub_num", length = 5)
    private String buildingSubNum;

    // 기초구역번호
    @Column(name = "basic_zone_num", length = 5)
    private String basicZoneNum;

    // 변경사유코드 (31:신규, 34:변경, 63:폐지)
    @Column(name = "change_reason_code", length = 2)
    private String changeReasonCode;

    // 공시일자 (NULL)
    @Column(name = "notification_date", length = 8)
    private String notificationDate;

    // 변경전 도로명주소 (NULL)
    @Column(name = "prev_road_name", length = 25)
    private String prevRoadName;

    // 상세주소 부여여부 (0:미부여, 1:부여)
    @Column(name = "detailed_address_assigned", length = 1)
    private String detailedAddressAssigned;

    private String buildingPurposeCode;
    private String buildingPurposeCodeName;
    private String detailPurposeCode;
    private String detailPurposeCodeName;
}