package com.view.zib.domain.transaction.repository;

import com.view.zib.domain.transaction.entity.BuildingTransaction;
import com.view.zib.domain.transaction.repository.jpa.BuildingTransactionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BuildingTransactionRepository {

    private final BuildingTransactionJpaRepository buildingTransactionJpaRepository;

    public void saveAll(List<BuildingTransaction> buildingTransactions) {
        buildingTransactionJpaRepository.saveAll(buildingTransactions);
    }
}
