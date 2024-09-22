package com.view.zib.domain.address.facade;

import com.view.zib.domain.address.service.RoadNameAddressUpdateService;
import com.view.zib.domain.client.kako.domain.Coordinate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RoadNameAddressCommandFacade {

    private final RoadNameAddressUpdateService roadNameAddressUpdateService;

    public void updateCoordinate(String managementNo, Coordinate coordinate) {
        roadNameAddressUpdateService.updateCoordinate(managementNo, coordinate);
    }
}
