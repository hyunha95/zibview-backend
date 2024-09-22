package com.view.zib.domain.transaction.service;

import com.view.zib.domain.transaction.entity.BuildingTransaction;

import java.util.List;

public interface BuildingTransactionCreateService {
    void create(List<BuildingTransaction> buildingTransactions);
}
