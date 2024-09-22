package com.view.zib.domain.address.event.publisher;

import com.view.zib.domain.address.event.JibunDetailEvent;
import com.view.zib.domain.client.vworld.dto.VWorldResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Component
public class JibunDetailEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishCreateEvent(Long jibunId, VWorldResponseDto vWorldResponseDto) {
        log.info("Publishing jibun detail create event - jibunId: [{}]", jibunId);
        JibunDetailEvent event = JibunDetailEvent.of(jibunId, vWorldResponseDto, LocalDateTime.now());
        applicationEventPublisher.publishEvent(event);
    }
}
