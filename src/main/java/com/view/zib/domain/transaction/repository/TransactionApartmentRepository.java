package com.view.zib.domain.transaction.repository;

import com.view.zib.domain.transaction.entity.TransactionApartment;
import com.view.zib.domain.transaction.repository.dto.DuplicateTransactionBuildingDTO;
import com.view.zib.domain.transaction.repository.jdbc.TransactionApartmentJdbcTemplate;
import com.view.zib.domain.transaction.repository.jpa.TransactionApartmentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Repository
public class TransactionApartmentRepository {

    private final TransactionApartmentJpaRepository transactionApartmentJpaRepository;
    private final TransactionApartmentJdbcTemplate transactionApartmentJdbcTemplate;

    public List<DuplicateTransactionBuildingDTO> findBySggCodesInAndDealYearAndDealMonthGroupBy(Set<String> sggCodes, String searchYear, String searchMonth) {
        return transactionApartmentJpaRepository.findBySggCodesInAndDealYearAndDealMonthGroupBy(sggCodes, searchYear, searchMonth);
    }

    public List<TransactionApartment> findBySggCodesInAndDealYearAndDealMonth(Set<String> sggCodes, String searchYear, String searchMonth) {
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
}
