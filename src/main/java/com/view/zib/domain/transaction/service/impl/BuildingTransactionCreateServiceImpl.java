package com.view.zib.domain.transaction.service.impl;

import com.view.zib.domain.transaction.entity.BuildingTransaction;
import com.view.zib.domain.transaction.repository.BuildingTransactionRepository;
import com.view.zib.domain.transaction.service.BuildingTransactionCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BuildingTransactionCreateServiceImpl implements BuildingTransactionCreateService {

    private final BuildingTransactionRepository buildingTransactionRepository;

    @Override
    public void create(List<BuildingTransaction> buildingTransactions) {
        buildingTransactionRepository.saveAll(buildingTransactions);
    }
}
