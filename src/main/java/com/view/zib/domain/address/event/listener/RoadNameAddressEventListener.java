package com.view.zib.domain.address.event.listener;

import com.view.zib.domain.address.event.RoadNameAddressUpdateCoordinateEvent;
import com.view.zib.domain.address.facade.RoadNameAddressCommandFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RoadNameAddressEventListener {

    private final RoadNameAddressCommandFacade roadNameAddressCommandFacade;

    @EventListener
    public void updateEvent(RoadNameAddressUpdateCoordinateEvent event) {
        log.info("Received RoadNameAddress update coordinate event - managementNo: [{}]", event.getManagementNo());
        roadNameAddressCommandFacade.updateCoordinate(event.getManagementNo(), event.getCoordinate());
    }
}
