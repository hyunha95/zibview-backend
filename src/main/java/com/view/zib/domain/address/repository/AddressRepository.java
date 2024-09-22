package com.view.zib.domain.address.repository;

import com.view.zib.domain.address.entity.Address;
import com.view.zib.domain.address.repository.jpa.AddressJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class AddressRepository  {

    private final AddressJpaRepository addressJpaRepository;

    public Optional<Address> findById(String addressId) {
        return addressJpaRepository.findById(addressId);
    }

    public Optional<Address> findByLegalDongCodeStartsWithAndSsgAndEmdAndJibunAndSubJibun(String sggCd, String sggNm, String umdNm, String jibun, String subJibun) {
        return addressJpaRepository.findByLegalDongCodeStartsWithAndSsgAndEmdAndJibunAndSubJibun(sggCd, sggNm, umdNm, jibun, subJibun);
    }
}
