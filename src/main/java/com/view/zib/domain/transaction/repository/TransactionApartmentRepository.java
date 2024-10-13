package com.view.zib.domain.transaction.repository;

import com.view.zib.domain.transaction.entity.TransactionApartment;
import com.view.zib.domain.transaction.repository.dto.DuplicateTransactionBuildingDTO;
import com.view.zib.domain.transaction.repository.jdbc.TransactionApartmentJdbcTemplate;
import com.view.zib.domain.transaction.repository.jpa.TransactionApartmentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Repository
public class TransactionApartmentRepository {

    private final TransactionApartmentJpaRepository transactionApartmentJpaRepository;
    private final TransactionApartmentJdbcTemplate transactionApartmentJdbcTemplate;

    public List<DuplicateTransactionBuildingDTO> findBySggCodesInAndDealYearAndDealMonthGroupBy(Set<String> sggCodes, int searchYear, int searchMonth) {
        return transactionApartmentJpaRepository.findBySggCodesInAndDealYearAndDealMonthGroupBy(sggCodes, searchYear, searchMonth);
    }

    public List<TransactionApartment> findBySggCodesInAndDealYearAndDealMonth(Set<String> sggCodes, int searchYear, int searchMonth) {
        return transactionApartmentJpaRepository.findBySggCodesInAndDealYearAndDealMonth(sggCodes, searchYear, searchMonth);
    }

    public List<TransactionApartment> findByJibunIdIn(List<Long> jibunIds) {
        return transactionApartmentJpaRepository.findByJibunIdIn(jibunIds);
    }

    public void bulkInsert(List<TransactionApartment> newTransactionApartments) {
        transactionApartmentJdbcTemplate.bulkInsert(newTransactionApartments);
    }

    public List<TransactionApartment> findByJibunIdInGroupByJibunIdOrderByDealYearAndDealMonth(List<Long> jibunIds) {
        return transactionApartmentJpaRepository.findByJibunIdInGroupByJibunIdOrderByDealYearAndDealMonth(jibunIds);
    }

    public List<TransactionApartment> findByJibunIdGroupByExclusiveUseAreaOrderByYMD(Long jibunId) {
        return transactionApartmentJpaRepository.findByJibunIdGroupByExclusiveUseAreaOrderByYMD(jibunId);
    }

    public Optional<TransactionApartment> findOneByJibunId(Long jibunId) {
        Page<TransactionApartment> oneByJibunId = transactionApartmentJpaRepository.findOneByJibunId(jibunId, PageRequest.of(0, 1));
        return oneByJibunId.getContent().stream().findFirst();
    }

    public List<TransactionApartment> findByJibunIdAndDealYearAfterAndExclusiveUseArea(Long jibunId, Integer fromYear, BigDecimal exclusiveUseArea) {
        return transactionApartmentJpaRepository.findByJibunIdAndDealYearAfterAndExclusiveUseArea(jibunId, fromYear, exclusiveUseArea);
    }
}
