package com.view.zib.domain.address.entity;

import com.view.zib.domain.location.entity.LocationBuilding;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
@Table(name = "road_name_address")
public class RoadNameAddress {

    @Id
    @Column(name = "road_name_address_id", nullable = false)
    private Long id;

    @Size(max = 25)
    @Column(name = "management_no", nullable = false, length = 25)
    private String managementNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "road_name_code_id")
    private RoadNameCode roadNameCode;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_building_id")
    private LocationBuilding locationBuilding;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "roadNameAddress")
    private List<Jibun> jibuns;

    @OneToOne
    @JoinColumn(name = "address_additional_info_id")
    private AddressAdditionalInfo addressAdditionalInfo;

    @Size(max = 1)
    @Column(name = "ground_yn", length = 1)
    private String groundYn;

    @Column(name = "building_main_no")
    private Integer buildingMainNo;

    @Column(name = "building_sub_no")
    private Integer buildingSubNo;

    @Size(max = 5)
    @Column(name = "post_code", length = 5)
    private String postCode;

    @Size(max = 2)
    @Column(name = "change_reason_code", length = 2)
    private String changeReasonCode;

    @Size(max = 8)
    @Column(name = "notification_date", length = 8)
    private String notificationDate;

    @Size(max = 25)
    @Column(name = "prev_road_name_address", length = 25)
    private String prevRoadNameAddress;

    @Size(max = 1)
    @Column(name = "detailed_address_yn", length = 1)
    private String detailedAddressYn;

    @Size(max = 2)
    @Column(name = "emd_serial_no", length = 2)
    private String emdSerialNo;

    public String getShortRoadNameAddress() {
        StringBuilder sb = new StringBuilder();

        sb.append(roadNameCode.getSidoName()).append(" ");

        if (!StringUtils.isBlank(roadNameCode.getSggName())) {
            sb.append(roadNameCode.getSggName()).append(" ");
        }

        // 읍면동구분 (0: 읍면, 1: 동, 2: 미부여)
        if ("0".equals(roadNameCode.getEmdDivision())) {
            sb.append(roadNameCode.getEmdName());
        } else {
            sb.append(roadNameCode.getEmdName()).append(" ");
        }

        sb.append(roadNameCode.getRoadName()).append(" ");
        sb.append(this.buildingMainNo);
        if (this.buildingSubNo > 0) sb.append("-").append(this.buildingSubNo);

        return sb.toString();
    }

    /**
     * 도로명 주소를 반환한다.
     * @return
     */
    public String getRoadNameAddress() {
        StringBuilder sb = new StringBuilder();

        sb.append(roadNameCode.getSidoName()).append(" ");

        if (!StringUtils.isBlank(roadNameCode.getSggName())) {
            sb.append(roadNameCode.getSggName()).append(" ");
        }

        // 읍면동구분 (0: 읍면, 1: 동, 2: 미부여)
        if ("0".equals(roadNameCode.getEmdDivision())) {
            sb.append(roadNameCode.getEmdName());
        } else {
            sb.append(roadNameCode.getEmdName()).append(" ");
        }

        sb.append(roadNameCode.getRoadName()).append(" ");

        if ("1".equals(this.groundYn)) sb.append("지상").append(" ");
        if ("2".equals(this.groundYn)) sb.append("공중").append(" ");
        sb.append(this.buildingMainNo);
        if (this.buildingSubNo > 0) sb.append("-").append(this.buildingSubNo);

        if ("0".equals(roadNameCode.getEmdDivision()) && addressAdditionalInfo.isApartment()) {
            if (!StringUtils.isBlank(addressAdditionalInfo.getSggBuildingName())) {
                sb.append("(").append(addressAdditionalInfo.getSggBuildingName()).append(")");
            }
        } else if ("1".equals(roadNameCode.getEmdDivision()) && !addressAdditionalInfo.isApartment()) {
            sb.append("(").append(roadNameCode.getEmdName()).append(")");
        } else if ("1".equals(roadNameCode.getEmdDivision()) && addressAdditionalInfo.isApartment()) {
            sb.append("(").append(roadNameCode.getEmdName());
            if (!StringUtils.isBlank(addressAdditionalInfo.getSggBuildingName())) {
                sb.append(",").append(addressAdditionalInfo.getSggBuildingName());
            }
        }

        return sb.toString();
    }
}