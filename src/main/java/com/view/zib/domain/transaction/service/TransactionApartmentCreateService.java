package com.view.zib.domain.transaction.service;

import com.view.zib.domain.address.entity.Jibun;
import com.view.zib.domain.client.vworld.dto.ApartmentTransactionResponse;
import com.view.zib.domain.transaction.entity.TransactionApartment;

import java.util.List;

public interface TransactionApartmentCreateService {
    void create(Jibun jibun, List<ApartmentTransactionResponse.Item> items);

    void create(List<TransactionApartment> newTransactionApartments);

}
