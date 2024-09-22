package com.view.zib.domain.transaction.facade;

import com.view.zib.domain.transaction.entity.BuildingTransaction;
import com.view.zib.domain.transaction.service.BuildingTransactionCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class BuildingTransactionCommandFacade {
    private final BuildingTransactionCreateService buildingTransactionCreateService;

    public void create(List<BuildingTransaction> buildingTransactions) {
        buildingTransactionCreateService.create(buildingTransactions);
    }
}
