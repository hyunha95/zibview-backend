package com.view.zib.domain.transaction.controller;

import com.view.zib.domain.transaction.facade.BuildingTransactionQueryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/building-transactions")
public class BuildingTransactionQueryController {

    private final BuildingTransactionQueryFacade buildingTransactionQueryFacade;


}
