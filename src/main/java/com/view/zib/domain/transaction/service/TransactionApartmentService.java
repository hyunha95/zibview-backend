package com.view.zib.domain.transaction.service;

import com.view.zib.domain.transaction.entity.TransactionApartment;
import com.view.zib.domain.transaction.repository.dto.DuplicateTransactionBuildingDTO;

import java.util.List;
import java.util.Set;

public interface TransactionApartmentService {
    List<DuplicateTransactionBuildingDTO> findBySggCodesInAndDealYearAndDealMonthGroupBy(Set<String> sggCodes, String searchYear, String searchMonth);

    List<TransactionApartment> findBySggCodesInAndDealYearAndDealMonth(Set<String> sggCodes, String searchYear, String searchMonth);

    List<TransactionApartment> findByJibunIdIn(List<Long> jibunIds);

    List<TransactionApartment> findByJibunIdInGroupByJibunIdOrderByDealYearAndDealMonth(List<Long> jibunIds);

    List<TransactionApartment> findByJibunIdGroupByExclusiveUseAreaOrderByYMD(Long jibunId);
}
