package com.view.zib.domain.transaction.repository.jpa;

import com.view.zib.domain.transaction.entity.TransactionApartment;
import com.view.zib.domain.transaction.repository.custom.TransactionApartmentCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface TransactionApartmentJpaRepository extends JpaRepository<TransactionApartment, Long>, TransactionApartmentCustomRepository {

    @Query("SELECT ta FROM TransactionApartment ta WHERE ta.sggCode IN :sggCodes AND ta.dealYear = :searchYear AND ta.dealMonth = :searchMonth")
    List<TransactionApartment> findBySggCodesInAndDealYearAndDealMonth(Set<String> sggCodes, int searchYear, int searchMonth);

    @Query("SELECT ta FROM TransactionApartment ta WHERE ta.jibun.id IN :jibunIds")
    List<TransactionApartment> findByJibunIdIn(List<Long> jibunIds);

    @Query("SELECT ta FROM TransactionApartment ta WHERE ta.jibun.id IN :jibunIds AND ta.dealYear >= :year AND ta.dealMonth >= :month GROUP BY ta.jibun.id")
    List<TransactionApartment> findByJibunIdInAndYearMonthGroupByJibunId(Set<Long> jibunIds, int year, int month);

    @Query("SELECT ta FROM TransactionApartment ta WHERE ta.jibun.id = :jibunId GROUP BY ta.exclusiveUseArea ORDER BY ta.dealYear desc, ta.dealMonth desc, ta.dealDay desc")
    List<TransactionApartment> findByJibunIdGroupByExclusiveUseAreaOrderByYMD(Long jibunId);

    @Query("SELECT ta FROM TransactionApartment ta WHERE ta.jibun.id = :jibunId")
    Page<TransactionApartment> findOneByJibunId(Long jibunId, Pageable pageable);

    List<TransactionApartment> findByJibunIdAndDealYearAfterAndExclusiveUseArea(Long jibunId, Integer fromYear, BigDecimal exclusiveUseArea);

}
