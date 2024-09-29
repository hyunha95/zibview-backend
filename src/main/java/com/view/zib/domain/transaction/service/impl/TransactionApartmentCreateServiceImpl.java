package com.view.zib.domain.transaction.service.impl;

import com.view.zib.domain.address.entity.Jibun;
import com.view.zib.domain.client.vworld.dto.ApartmentTransactionResponse;
import com.view.zib.domain.transaction.entity.TransactionApartment;
import com.view.zib.domain.transaction.repository.TransactionApartmentRepository;
import com.view.zib.domain.transaction.service.TransactionApartmentCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionApartmentCreateServiceImpl implements TransactionApartmentCreateService {

    private final TransactionApartmentRepository transactionApartmentRepository;

    @Override
    public void create(Jibun jibun, List<ApartmentTransactionResponse.Item> items) {
        List<TransactionApartment> newTransactionApartments = items.stream()
                .map(item -> TransactionApartment.from(jibun, item))
                .toList();

        transactionApartmentRepository.saveAll(newTransactionApartments);
    }
}
