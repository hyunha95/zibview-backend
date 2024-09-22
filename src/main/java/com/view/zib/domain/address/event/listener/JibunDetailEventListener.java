package com.view.zib.domain.address.event.listener;

import com.view.zib.domain.address.event.JibunDetailEvent;
import com.view.zib.domain.address.facade.JibunDetailCommandFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class JibunDetailEventListener {

    private final JibunDetailCommandFacade jibunDetailCommandFacade;

    @EventListener
    public void createEvent(JibunDetailEvent event) {
        log.info("Received jibun detail create event - jibunId: [{}]", event.getJibunId());
        jibunDetailCommandFacade.create(event.getJibunId(), event.getVWorldResponseDto());
    }
}
