package com.view.zib.domain.address.repository.custom;

import com.view.zib.domain.address.entity.Address;

import java.util.Optional;

public interface AddressJdbcClient {

    Optional<Address> getByIdForUpdateSkipLocked(String addressId);
}
