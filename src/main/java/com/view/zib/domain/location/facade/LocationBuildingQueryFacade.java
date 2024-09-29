package com.view.zib.domain.location.facade;

import com.view.zib.domain.location.service.LocationBuildingQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LocationBuildingQueryFacade {

    private final LocationBuildingQueryService locationBuildingQueryService;

}
