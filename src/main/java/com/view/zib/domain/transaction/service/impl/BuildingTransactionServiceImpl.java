package com.view.zib.domain.transaction.service.impl;

import com.view.zib.domain.transaction.repository.BuildingTransactionRepository;
import com.view.zib.domain.transaction.service.BuildingTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BuildingTransactionServiceImpl implements BuildingTransactionService {

    private final BuildingTransactionRepository buildingTransactionRepository;

}
