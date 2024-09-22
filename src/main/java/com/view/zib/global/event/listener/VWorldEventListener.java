package com.view.zib.global.event.listener;

import com.view.zib.domain.address.facade.AddressDetailCommandFacade;
import com.view.zib.domain.client.vworld.dto.VWorldResponseDto;
import com.view.zib.global.event.EventType;
import com.view.zib.global.event.GenericEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class VWorldEventListener {

    private final AddressDetailCommandFacade addressDetailCommandFacade;

    @EventListener(GenericEvent.class)
    public void onApplicationEvent(@NonNull GenericEvent<String, VWorldResponseDto> event) {
        if (event.getEventType() == EventType.AddressDetail.SEARCHED) {
            log.info("Received spring address event - addressId: [{}], eventType: [{}]", event.getId(), event.getEventType());
            addressDetailCommandFacade.create(event.getId(), event.getWhat());
        }
    }
}
