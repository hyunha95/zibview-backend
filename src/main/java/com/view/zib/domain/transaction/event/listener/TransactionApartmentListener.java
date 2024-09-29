package com.view.zib.domain.transaction.event.listener;

import com.view.zib.domain.transaction.event.TransactionApartmentSearchEvent;
import com.view.zib.domain.transaction.facade.TransactionApartmentCommandFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class TransactionApartmentListener {

    private final TransactionApartmentCommandFacade transactionApartmentCommandFacade;

    @EventListener
    public void onApplicationEvent(TransactionApartmentSearchEvent transactionApartmentSearchEvent) {
        log.info("Received spring transaction apartment search event");
        transactionApartmentCommandFacade.create(transactionApartmentSearchEvent.getItems());
    }
}
