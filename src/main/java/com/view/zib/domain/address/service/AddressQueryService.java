package com.view.zib.domain.address.service;

import com.view.zib.domain.address.entity.Address;

import java.util.Optional;

public interface AddressQueryService {

    Address getById(String addressId);
    Address getByIdForUpdateSkipLocked(String addressId);

    Optional<Address> findByLegalDongCodeStartsWithAndSsgAndEmdAndJibunAndSubJibun(String sggCd, String sggNm, String umdNm, String jibun, String subJibun);
}
