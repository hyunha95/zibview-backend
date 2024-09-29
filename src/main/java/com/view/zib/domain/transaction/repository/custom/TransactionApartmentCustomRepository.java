package com.view.zib.domain.transaction.repository.custom;

import com.view.zib.domain.transaction.repository.dto.DuplicateTransactionBuildingDTO;

import java.util.List;
import java.util.Set;

public interface TransactionApartmentCustomRepository {
    List<DuplicateTransactionBuildingDTO> findBySggCodesInAndDealYearAndDealMonthGroupBy(Set<String> sggCodes, String searchYear, String searchMonth);
}
