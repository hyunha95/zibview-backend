package com.view.zib.global.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class GenericEventPublisher<ID, T> {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(ID id, T what, EventType eventType) {
        log.info("Publishing generic event. id: [{}], eventType: [{}]", id, eventType);
        GenericEvent<ID, T> genericEvent = new GenericEvent<>(id, what, eventType);
        applicationEventPublisher.publishEvent(genericEvent);
    }
}
