package com.view.zib.domain.address.repository.custom.impl;

import com.view.zib.domain.address.entity.Address;
import com.view.zib.domain.address.repository.AddressRepository;
import com.view.zib.domain.address.repository.custom.AddressJdbcClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@Sql(value = "/sql/address-jdbc-client-impl-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AddressJdbcClientImplTest {

    @Qualifier("addressJdbcClientImpl")
    @Autowired
    AddressJdbcClient addressJdbcClient;

    @Autowired
    AddressRepository addressRepository;

    @Test
    void 조회한_행이_lock_되어_있다면_Optioanl_empty_반환() {
        // given
        String addressId = "00000778-7e72-4840-91dc-45ee87fa2520";

        // when
//        Address lockedAddress = addressRepository.getByIdForUpdate(addressId).get();

        Optional<Address> skipLockedAddress = addressJdbcClient.getByIdForUpdateSkipLocked(addressId);

        // then
        assertThat(skipLockedAddress).isEmpty();
    }

}