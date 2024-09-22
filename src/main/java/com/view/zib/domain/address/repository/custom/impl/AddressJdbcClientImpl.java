package com.view.zib.domain.address.repository.custom.impl;

import com.view.zib.domain.address.entity.Address;
import com.view.zib.domain.address.repository.custom.AddressJdbcClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class AddressJdbcClientImpl implements AddressJdbcClient {

    private final JdbcClient jdbcClient;

    @Override
    public Optional<Address> getByIdForUpdateSkipLocked(String addressId) {
        String sql = "SELECT * FROM address WHERE address_id = :addressId FOR UPDATE SKIP LOCKED";
        return jdbcClient.sql(sql).query(Address.class).optional();
    }
}
