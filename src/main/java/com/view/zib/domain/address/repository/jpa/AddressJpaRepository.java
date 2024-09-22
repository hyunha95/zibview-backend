package com.view.zib.domain.address.repository.jpa;

import com.view.zib.domain.address.entity.Address;
import com.view.zib.domain.address.repository.custom.AddressJdbcClient;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AddressJpaRepository extends JpaRepository<Address, String>, AddressJdbcClient {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Address a WHERE a.id = :addressId")
    Optional<Address> getByIdForUpdate(String addressId);

    @Query("SELECT a FROM Address a WHERE a.legalDongCode LIKE :sggCd% AND a.sigungu = :sggNm AND a.emd = :umdNm AND a.jibun = :jibun AND a.subJibun = :subJibun")
    Optional<Address> findByLegalDongCodeStartsWithAndSsgAndEmdAndJibunAndSubJibun(String sggCd, String sggNm, String umdNm, String jibun, String subJibun);
}
