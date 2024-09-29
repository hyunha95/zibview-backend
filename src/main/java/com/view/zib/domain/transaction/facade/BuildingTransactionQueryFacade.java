package com.view.zib.domain.transaction.facade;

import com.view.zib.domain.transaction.service.BuildingTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BuildingTransactionQueryFacade {

    private final BuildingTransactionService buildingTransactionService;

}
