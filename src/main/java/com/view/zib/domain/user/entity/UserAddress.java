package com.view.zib.domain.user.entity;

import com.view.zib.domain.address.entity.Address;
import com.view.zib.domain.post.controller.request.PostRequest;
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
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    private LocalDate contractStartDate;
    private LocalDate contractEndDate;


    public static UserAddress of(User user, Address address, PostRequest.ContractInfo contractInfo) {
        UserAddress userAddress = UserAddress.builder()
                .user(user)
                .address(address)
                .contractStartDate(contractInfo.getContractStartDate())
                .contractEndDate(contractInfo.getContractEndDate())
                .build();

        return userAddress;
    }
}
