package com.view.zib.domain.transaction.event;

import com.view.zib.domain.client.vworld.dto.ApartmentTransactionResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class TransactionApartmentSearchEvent {
    private List<ApartmentTransactionResponse.Item> items;
}
