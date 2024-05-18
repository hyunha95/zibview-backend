package com.view.zib.domain.user.entity;

import com.view.zib.domain.address.entity.Address;
import com.view.zib.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserAddress extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_address_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    private LocalDate residenceStartDate;
    private LocalDate residenceEndDate;


    public static UserAddress from(UserEntity user, Address address, LocalDate residencyStartDate, LocalDate residencyEndDate) {
        UserAddress userAddress = UserAddress.builder()
                .userEntity(user)
                .address(address)
                .residenceStartDate(residencyStartDate)
                .residenceEndDate(residencyEndDate)
                .build();

        // 연관관계 세팅
        user.getUserAddresses().add(userAddress);

        return userAddress;
    }
}
