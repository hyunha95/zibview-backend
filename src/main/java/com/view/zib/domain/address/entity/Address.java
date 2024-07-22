package com.view.zib.domain.address.entity;

import com.view.zib.domain.api.kako.domain.Coordinate;
import com.view.zib.domain.post.entity.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Entity
public class Address {

    @Id
    @Size(max = 36)
    @Column(name = "address_id", nullable = false, length = 36)
    private String addressId;

    @OneToOne(mappedBy = "address")
    private Post post;

    @Size(max = 26)
    @Column(name = "management_number", length = 26)
    private String managementNumber;

    @Size(max = 19)
    @Column(name = "pnu", length = 19)
    private String pnu;

    @Size(max = 255)
    @Column(name = "road_name_address")
    private String roadNameAddress;

    @Size(max = 255)
    @Column(name = "jibun_address")
    private String jibunAddress;

    @Size(max = 10)
    @Column(name = "legal_dong_code", length = 10)
    private String legalDongCode;

    @Size(max = 40)
    @Column(name = "sido", length = 40)
    private String sido;

    @Size(max = 40)
    @Column(name = "sigungu", length = 40)
    private String sigungu;

    @Size(max = 40)
    @Column(name = "emd", length = 40)
    private String emd;

    @Size(max = 40)
    @Column(name = "ri", length = 40)
    private String ri;

    @Column(name = "mountain")
    private Character mountain;

    @Column(name = "jibun_number")
    private Integer jibunNumber;

    @Column(name = "sub_jibun_number")
    private Integer subJibunNumber;

    @Size(max = 12)
    @Column(name = "road_name_code", length = 12)
    private String roadNameCode;

    @Size(max = 80)
    @Column(name = "road_name", length = 80)
    private String roadName;

    @Column(name = "underground")
    private Character underground;

    @Column(name = "building_number")
    private Integer buildingNumber;

    @Column(name = "sub_building_number")
    private Integer subBuildingNumber;

    @Size(max = 60)
    @Column(name = "hjd_code", length = 60)
    private String hjdCode;

    @Size(max = 60)
    @Column(name = "hjd_name", length = 60)
    private String hjdName;

    @Size(max = 5)
    @Column(name = "zip_code", length = 5)
    private String zipCode;

    @Size(max = 400)
    @Column(name = "prev_road_name_address", length = 400)
    private String prevRoadNameAddress;

    @Size(max = 8)
    @Column(name = "effected_at", length = 8)
    private String effectedAt;

    @Column(name = "apartment_building")
    private Character apartmentBuilding;

    @Size(max = 2)
    @Column(name = "movement_reason", length = 2)
    private String movementReason;

    @Size(max = 400)
    @Column(name = "building_name", length = 400)
    private String buildingName;

    @Size(max = 400)
    @Column(name = "sigungu_building_name", length = 400)
    private String sigunguBuildingName;

    @Size(max = 200)
    @Column(name = "note", length = 200)
    private String note;

    @Column(name = "latitude", precision = 13, scale = 10)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 13, scale = 10)
    private BigDecimal longitude;

    public void updateCoordinate(Coordinate coordinate) {
        this.latitude = coordinate.latitude();
        this.longitude = coordinate.longitude();
    }
}