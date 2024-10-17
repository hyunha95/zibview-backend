package com.view.zib.domain.transaction.facade;

import com.view.zib.domain.address.repository.dto.TransactionApartmentDTO;
import com.view.zib.domain.transaction.entity.TransactionApartment;
import com.view.zib.domain.transaction.service.TransactionApartmentCreateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class TransactionApartmentCommandFacade {
    private final TransactionApartmentCreateService transactionApartmentCreateService;

    public void create(List<TransactionApartment> transactionApartments) {
        transactionApartmentCreateService.create(transactionApartments);
    }

    public void bulkInsert(List<TransactionApartmentDTO> newTransactionApartmentDTOs) {
        transactionApartmentCreateService.bulkInsert(newTransactionApartmentDTOs);
    }
}
