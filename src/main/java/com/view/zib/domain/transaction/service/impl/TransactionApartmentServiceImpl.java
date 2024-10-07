package com.view.zib.domain.transaction.service.impl;

import com.view.zib.domain.transaction.entity.TransactionApartment;
import com.view.zib.domain.transaction.repository.TransactionApartmentRepository;
import com.view.zib.domain.transaction.repository.dto.DuplicateTransactionBuildingDTO;
import com.view.zib.domain.transaction.service.TransactionApartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TransactionApartmentServiceImpl implements TransactionApartmentService {

    private final TransactionApartmentRepository transactionApartmentRepository;

    @Override
    public List<DuplicateTransactionBuildingDTO> findBySggCodesInAndDealYearAndDealMonthGroupBy(Set<String> sggCodes, String searchYear, String searchMonth) {
        searchMonth = searchMonth.startsWith("0") ? searchMonth.substring(1) : searchMonth;
        return transactionApartmentRepository.findBySggCodesInAndDealYearAndDealMonthGroupBy(sggCodes, searchYear, searchMonth);
    }

    @Override
    public List<TransactionApartment> findBySggCodesInAndDealYearAndDealMonth(Set<String> sggCodes, String searchYear, String searchMonth) {
        searchMonth = searchMonth.startsWith("0") ? searchMonth.substring(1) : searchMonth;
        return transactionApartmentRepository.findBySggCodesInAndDealYearAndDealMonth(sggCodes, searchYear, searchMonth);
    }

    @Override
    public List<TransactionApartment> findByJibunIdIn(List<Long> jibunIds) {
        return transactionApartmentRepository.findByJibunIdIn(jibunIds);
    }

    @Override
    public List<TransactionApartment> findByJibunIdInGroupByJibunIdOrderByDealYearAndDealMonth(List<Long> jibunIds) {
        return transactionApartmentRepository.findByJibunIdInGroupByJibunIdOrderByDealYearAndDealMonth(jibunIds);
    }

    @Override
    public List<TransactionApartment> findByJibunIdGroupByExclusiveUseAreaOrderByYMD(Long jibunId) {
        return transactionApartmentRepository.findByJibunIdGroupByExclusiveUseAreaOrderByYMD(jibunId);
    }
}
