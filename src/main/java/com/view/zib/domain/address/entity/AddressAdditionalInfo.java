package com.view.zib.domain.address.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "address_additional_info")
public class AddressAdditionalInfo {
    @Id
    @Column(name = "address_additional_info_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "management_no")
    private Jibun jibun;

    @Size(max = 10)
    @Column(name = "hjd_code", length = 10)
    private String hjdCode;

    @Size(max = 20)
    @Column(name = "hjd_name", length = 20)
    private String hjdName;

    @Size(max = 5)
    @Column(name = "post_code", length = 5)
    private String postCode;

    @Size(max = 3)
    @Column(name = "post_code_serial_no", length = 3)
    private String postCodeSerialNo;

    @Size(max = 40)
    @Column(name = "mass_delivery_name", length = 40)
    private String massDeliveryName;

    @Size(max = 40)
    @Column(name = "building_name", length = 40)
    private String buildingName;

    @Size(max = 40)
    @Column(name = "sgg_building_name", length = 40)
    private String sggBuildingName;

    @Size(max = 1)
    @Column(name = "apartment", length = 1, columnDefinition = "tinyint(1)")
    private boolean apartment;

    @Size(max = 1)
    @Column(name = "representative", length = 1, columnDefinition = "tinyint(1)")
    private boolean representative;

    public String getNotEmptyBuildingName() {
        return buildingName.isBlank() ? sggBuildingName : buildingName;
    }
}