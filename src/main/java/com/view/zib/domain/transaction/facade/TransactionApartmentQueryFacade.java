package com.view.zib.domain.transaction.facade;

import com.view.zib.domain.transaction.entity.TransactionApartment;
import com.view.zib.domain.transaction.repository.dto.DuplicateTransactionBuildingDTO;
import com.view.zib.domain.transaction.service.TransactionApartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class TransactionApartmentQueryFacade {

    private final TransactionApartmentService transactionApartmentService;

    public List<DuplicateTransactionBuildingDTO> findBySggCodesInAndDealYearAndDealMonthGroupBy(Set<String> sggCodes, String searchYear, String searchMonth) {
        return transactionApartmentService.findBySggCodesInAndDealYearAndDealMonthGroupBy(sggCodes, searchYear, searchMonth);
    }

    public List<TransactionApartment> findBySggCodesInAndDealYearAndDealMonth(Set<String> sggCodes, String searchYear, String searchMonth) {
        return transactionApartmentService.findBySggCodesInAndDealYearAndDealMonth(sggCodes, searchYear, searchMonth);
    }

    public List<TransactionApartment> findByJibunIdIn(List<Long> jibunIds) {
        return transactionApartmentService.findByJibunIdIn(jibunIds);
    }

    public List<TransactionApartment> findByJibunIdInGroupByJibunIdOrderByDealYearAndDealMonth(List<Long> jibunIds) {
        return transactionApartmentService.findByJibunIdInGroupByJibunIdOrderByDealYearAndDealMonth(jibunIds);
    }

    public List<TransactionApartment> findByJibunIdGroupByExclusiveUseAreaOrderByYMD(Long jibunId) {
        return transactionApartmentService.findByJibunIdGroupByExclusiveUseAreaOrderByYMD(jibunId);
    }

    public Optional<TransactionApartment> findOneByJibunId(Long id) {
    }
}
