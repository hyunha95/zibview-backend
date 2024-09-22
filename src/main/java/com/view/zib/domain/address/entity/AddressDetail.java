package com.view.zib.domain.address.entity;

import com.view.zib.domain.client.vworld.dto.VWorldResponseDto;
import com.view.zib.global.jpa.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.domain.Persistable;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "address_detail")
public class AddressDetail extends BaseEntity implements Persistable<String> {
    @Id @Size(max = 36)
    @Column(name = "address_detail_id", nullable = false, length = 36)
    private String id;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Size(max = 100)
    @Column(name = "main_purpose_code_name", length = 100)
    private String mainPurposeCodeName;

    @Size(max = 25)
    @Column(name = "main_purpose_code", length = 25)
    private String mainPurposeCode;

    @Size(max = 500)
    @Column(name = "etc_purpose_name", length = 500)
    private String etcPurposeName;

    @Size(max = 2)
    @Column(name = "roof_code", length = 2)
    private String roofCode;

    @Size(max = 100)
    @Column(name = "root_code_name", length = 100)
    private String rootCodeName;

    @Size(max = 500)
    @Column(name = "etc_root_name", length = 500)
    private String etcRootName;

    @Column(name = "house_hold_count")
    private Integer houseHoldCount;

    @Column(name = "family_count")
    private Integer familyCount;

    @Column(name = "height", precision = 19, scale = 9)
    private BigDecimal height;

    @Column(name = "ground_floor_count")
    private Integer groundFloorCount;

    @Column(name = "underground_floor_count")
    private Integer undergroundFloorCount;

    @Column(name = "elevator_count")
    private Integer elevatorCount;

    @Column(name = "emergency_elevator_count")
    private Integer emergencyElevatorCount;

    @Column(name = "indoor_mechanical_parking_count")
    private Integer indoorMechanicalParkingCount;

    @Column(name = "outdoor_mechanical_parking_count")
    private Integer outdoorMechanicalParkingCount;

    @Column(name = "indoor_self_parking_count")
    private Integer indoorSelfParkingCount;

    @Column(name = "outdoor_self_parking_count")
    private Integer outdoorSelfParkingCount;

    @Column(name = "ho_count")
    private Integer hoCount;

    @Column(name = "structure_code")
    private Character structureCode;

    @Size(max = 100)
    @Column(name = "structure_code_name", length = 100)
    private String structureCodeName;

    @Size(max = 500)
    @Column(name = "etc_structure", length = 500)
    private String etcStructure;

    @Column(name = "earthquake_resistant")
    private Boolean earthquakeResistant;

    @Size(max = 200)
    @Column(name = "earthquake_resistant_ability", length = 200)
    private String earthquakeResistantAbility;

    @Override
    public boolean isNew() {
        return createdAt == null;
    }

    public static AddressDetail of(Address address, VWorldResponseDto vWorldResponseDto) {
        VWorldResponseDto.Item item = vWorldResponseDto.response().body().items().item();
        AddressDetail addressDetail = AddressDetail.builder()
                .id(UUID.randomUUID().toString())
                .address(address)
                .mainPurposeCodeName(item.mainPurposeCodeName())
                .mainPurposeCode(item.mainPurposeCode())
                .etcPurposeName(item.etcPurposeName())
                .roofCode(item.roofCode())
                .rootCodeName(item.rootCodeName())
                .etcRootName(item.etcRootName())
                .houseHoldCount(item.houseHoldCount())
                .familyCount(item.familyCount())
                .height(item.height())
                .groundFloorCount(item.groundFloorCount())
                .undergroundFloorCount(item.undergroundFloorCount())
                .elevatorCount(item.elevatorCount())
                .emergencyElevatorCount(item.emergencyElevatorCount())
                .indoorMechanicalParkingCount(item.indoorMechanicalParkingCount())
                .outdoorMechanicalParkingCount(item.outdoorMechanicalParkingCount())
                .indoorSelfParkingCount(item.indoorSelfParkingCount())
                .outdoorSelfParkingCount(item.outdoorSelfParkingCount())
                .hoCount(item.hoCount())
                .structureCode(item.structureCode())
                .structureCodeName(item.structureCodeName())
                .etcStructure(item.etcStructure())
                .earthquakeResistant(item.earthquakeResistant())
                .earthquakeResistantAbility(item.earthquakeResistantAbility())
                .build();

        address.addEntity(addressDetail);
        return addressDetail;
    }
}