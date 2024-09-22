package com.view.zib.domain.address.event.publisher;

import com.view.zib.domain.address.event.RoadNameAddressUpdateCoordinateEvent;
import com.view.zib.domain.client.kako.domain.Coordinate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Component
public class RoadNameAddressEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishUpdateCoordinateEvent(String managementNo, Coordinate coordinate, LocalDateTime now) {
        log.info("Publishing RoadNameAddress update coordinate event");
        RoadNameAddressUpdateCoordinateEvent event = RoadNameAddressUpdateCoordinateEvent.of(managementNo, coordinate, now);
        applicationEventPublisher.publishEvent(event);
    }
}
