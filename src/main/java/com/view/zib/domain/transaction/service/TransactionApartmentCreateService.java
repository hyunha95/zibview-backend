package com.view.zib.domain.transaction.service;

import com.view.zib.domain.address.entity.Jibun;
import com.view.zib.domain.address.repository.dto.TransactionApartmentDTO;
import com.view.zib.domain.client.vworld.dto.ApartmentTransactionResponse;

import java.util.List;

public interface TransactionApartmentCreateService {
    void create(Jibun jibun, List<ApartmentTransactionResponse.Item> items);

    void create(List<TransactionApartmentDTO> transactionApartmentDTOs);

}
