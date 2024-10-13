package com.view.zib.domain.transaction.facade;

import com.view.zib.domain.address.repository.dto.TransactionApartmentDTO;
import com.view.zib.domain.transaction.service.TransactionApartmentCreateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class TransactionApartmentCommandFacade {
    private final TransactionApartmentCreateService transactionApartmentCreateService;

    @Transactional
    public void create(List<TransactionApartmentDTO> transactionApartmentDTOs) {
        transactionApartmentCreateService.create(transactionApartmentDTOs);
    }
}
