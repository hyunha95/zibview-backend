package com.view.zib.domain.location.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "location_building")
public class LocationBuilding {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_building_id", nullable = false)
    private Long id;

    @Size(max = 10)
    @Column(name = "entrance_serial_number", length = 10)
    private String entranceSerialNumber;

    @Size(max = 10)
    @NotNull
    @Column(name = "legal_dong_code", nullable = false, length = 10)
    private String legalDongCode;

    @Size(max = 40)
    @Column(name = "sido_name", length = 40)
    private String sidoName;

    @Size(max = 40)
    @Column(name = "sgg_name", length = 40)
    private String sggName;

    @Size(max = 40)
    @Column(name = "emd_name", length = 40)
    private String emdName;

    @Size(max = 12)
    @NotNull
    @Column(name = "road_name_code", nullable = false, length = 12)
    private String roadNameCode;

    @Size(max = 80)
    @Column(name = "road_name", length = 80)
    private String roadName;

    @NotNull
    @Column(name = "basement_flag", nullable = false)
    private String basementFlag;

    @NotNull
    @Column(name = "building_main_no", nullable = false)
    private Integer buildingMainNo;

    @NotNull
    @Column(name = "building_sub_no", nullable = false)
    private Integer buildingSubNo;

    @Size(max = 40)
    @Column(name = "building_name", length = 40)
    private String buildingName;

    @Size(max = 5)
    @Column(name = "post_code", length = 5)
    private String postCode;

    @Size(max = 100)
    @Column(name = "building_usage", length = 100)
    private String buildingUsage;

    @Column(name = "building_group_flag")
    private Character buildingGroupFlag;

    @Size(max = 8)
    @Column(name = "admin_dong", length = 8)
    private String adminDong;

    @Column(name = "x_coordinate", precision = 15, scale = 6)
    private BigDecimal xCoordinate;

    @Column(name = "y_coordinate", precision = 15, scale = 6)
    private BigDecimal yCoordinate;

    @Size(max = 5)
    @NotNull
    @Column(name = "sgg_code", nullable = false, length = 5)
    private String sggCode;

}