package com.view.zib.domain.transaction.event.listener;

import com.view.zib.domain.transaction.event.TransactionApartmentSearchEvent;
import com.view.zib.domain.transaction.facade.TransactionApartmentCommandFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Component
public class TransactionApartmentListener {

    private final TransactionApartmentCommandFacade transactionApartmentCommandFacade;

    @TransactionalEventListener
    public void onApplicationEvent(TransactionApartmentSearchEvent transactionApartmentSearchEvent) {
        log.info("Received spring transaction apartment search event");
//        transactionApartmentCommandFacade.create(transactionApartmentSearchEvent.getItems());
    }
}
