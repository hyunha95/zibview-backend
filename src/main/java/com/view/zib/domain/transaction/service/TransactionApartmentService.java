package com.view.zib.domain.transaction.service;

import com.view.zib.domain.transaction.entity.TransactionApartment;
import com.view.zib.domain.transaction.repository.dto.DuplicateTransactionBuildingDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TransactionApartmentService {
    List<DuplicateTransactionBuildingDTO> findBySggCodesInAndDealYearAndDealMonthGroupBy(Set<String> sggCodes, int searchYear, int searchMonth);

    List<TransactionApartment> findBySggCodesInAndDealYearAndDealMonth(Set<String> sggCodes, int searchYear, int searchMonth);

    List<TransactionApartment> findByJibunIdIn(List<Long> jibunIds);

    List<TransactionApartment> findByJibunIdInGroupByJibunIdOrderByDealYearAndDealMonth(List<Long> jibunIds);

    List<TransactionApartment> findByJibunIdGroupByExclusiveUseAreaOrderByYMD(Long jibunId);

    Optional<TransactionApartment> findOneByJibunId(Long jibunId);

    List<TransactionApartment> findByJibunIdAndDealYearAfterAndExclusiveUseArea(Long jibunId, Integer fromYear, BigDecimal exclusiveUseArea);
}
