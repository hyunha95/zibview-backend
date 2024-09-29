package com.view.zib.domain.transaction.repository.jpa;

import com.view.zib.domain.transaction.entity.TransactionApartment;
import com.view.zib.domain.transaction.repository.custom.TransactionApartmentCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface TransactionApartmentJpaRepository extends JpaRepository<TransactionApartment, Long>, TransactionApartmentCustomRepository {

    @Query("SELECT ta FROM TransactionApartment ta WHERE ta.sggCode IN :sggCodes AND ta.dealYear = :searchYear AND ta.dealMonth = :searchMonth")
    List<TransactionApartment> findBySggCodesInAndDealYearAndDealMonth(Set<String> sggCodes, String searchYear, String searchMonth);

    @Query("SELECT ta FROM TransactionApartment ta WHERE ta.jibun.id IN :jibunIds")
    List<TransactionApartment> findByJibunIdIn(List<Long> jibunIds);

    @Query("SELECT ta FROM TransactionApartment ta WHERE ta.jibun.id IN :jibunIds GROUP BY ta.jibun.id ORDER BY ta.dealYear, ta.dealMonth")
    List<TransactionApartment> findByJibunIdInGroupByJibunIdOrderByDealYearAndDealMonth(List<Long> jibunIds);
}
