package com.view.zib.domain.transaction.event.publisher;

import com.view.zib.domain.address.entity.Jibun;
import com.view.zib.domain.client.vworld.dto.OfficeTelTransactionClientDTO;
import com.view.zib.domain.transaction.event.BuildingTransactionCreateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Component
public class BuildingTransactionPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishCreateEvent(Jibun jibun,  OfficeTelTransactionClientDTO officeTelTransactionClientDTO, LocalDateTime eventDateTime) {
        log.info("Publishing building transaction create event.");
        BuildingTransactionCreateEvent event = BuildingTransactionCreateEvent.of(jibun, officeTelTransactionClientDTO, eventDateTime);
        applicationEventPublisher.publishEvent(event);
    }
}
