package com.view.zib.domain.address.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "additional_info")
public class AdditionalInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "additional_info_id")
    private Long id;

    // 관리번호
    @Column(name = "management_num", length = 25)
    private String managementNum;

    // 행정동코드 (참고용)
    @Column(name = "hjd_code", length = 10)
    private String hjdCode;

    // 행정동명 (참고용)
    @Column(name = "hjd_name", length = 20)
    private String hjdName;

    // 우편번호
    @Column(name = "zipcode", length = 5)
    private String zipcode;

    // 우편번호 일련번호 (NULL)
    @Column(name = "zipcode_serial_num", length = 3)
    private String zipcodeSerialNum;

    // 다량배달처명 (NULL)
    @Column(name = "bulk_delivery_location_name", length = 40)
    private String bulkDeliveryLocationName;

    // 건물명
    @Column(name = "building_name", length = 40)
    private String buildingName;

    // 시군구 건물명
    @Column(name = "sigungu_building_name", length = 40)
    private String sigunguBuildingName;

    // 공동주택여부 (0:비공동주택, 1:공동주택)
    @Column(name = "multihouse", length = 1)
    private String multihouse;

    // Getters and Setters
}