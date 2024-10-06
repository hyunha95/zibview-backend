package com.view.zib.domain.transaction.event.publisher;

import com.view.zib.domain.client.vworld.dto.ApartmentTransactionResponse;
import com.view.zib.domain.transaction.event.TransactionApartmentSearchEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class TransactionApartmentPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(List<ApartmentTransactionResponse.Item> items) {
        if (CollectionUtils.isEmpty(items)) {
            return;
        }

        log.info("Publishing transaction apartment event.");
        applicationEventPublisher.publishEvent(new TransactionApartmentSearchEvent(items));
    }
}
