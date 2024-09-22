package com.view.zib.global.event.publisher;

import com.view.zib.global.event.AddressEvent;
import com.view.zib.global.event.AddressEventType;
import com.view.zib.global.event.listener.VWorldEventListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Component
public class AddressEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * @see VWorldEventListener
     */
    public void publishEvent(String addressId, Object message, AddressEventType eventType, LocalDateTime eventDateTime) {
        log.info("Publishing address event. addressId: [{}], eventType: [{}]", addressId, eventType);
        AddressEvent addressEvent = new AddressEvent(this, addressId, message, eventType, eventDateTime);
        applicationEventPublisher.publishEvent(addressEvent);
    }
}
