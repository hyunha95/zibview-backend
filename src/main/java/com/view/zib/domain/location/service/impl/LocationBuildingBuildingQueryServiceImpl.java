package com.view.zib.domain.location.service.impl;

import com.view.zib.domain.location.repository.LocationBuildingRepository;
import com.view.zib.domain.location.service.LocationBuildingQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LocationBuildingBuildingQueryServiceImpl implements LocationBuildingQueryService {

    private final LocationBuildingRepository locationBuildingRepository;

}
