package com.view.zib.domain.address.service.impl;

import com.view.zib.domain.address.entity.Address;
import com.view.zib.domain.address.repository.AddressRepository;
import com.view.zib.domain.address.service.AddressQueryService;
import com.view.zib.global.exception.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AddressQueryServiceImpl implements AddressQueryService {

    private final AddressRepository addressRepository;

    @Override
    public Address getById(String addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", addressId));
    }

    @Override
    public Address getByIdForUpdateSkipLocked(String addressId) {
        return null;
    }

    @Override
    public Optional<Address> findByLegalDongCodeStartsWithAndSsgAndEmdAndJibunAndSubJibun(String sggCd, String sggNm, String umdNm, String jibun, String subJibun) {
        return addressRepository.findByLegalDongCodeStartsWithAndSsgAndEmdAndJibunAndSubJibun(sggCd, sggNm, umdNm, jibun, subJibun);
    }
}
