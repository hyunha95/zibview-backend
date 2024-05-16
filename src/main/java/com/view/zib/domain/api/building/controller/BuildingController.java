package com.view.zib.domain.api.building.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.view.zib.domain.api.building.controller.request.AddressRequest;
import com.view.zib.domain.api.building.service.JusoApiService;
import com.view.zib.domain.api.building.controller.request.BuildingRequest;
import com.view.zib.domain.api.building.controller.response.BuildingUseResponse;
import com.view.zib.domain.api.building.service.VWorldService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RequiredArgsConstructor
@RequestMapping("/api/v1/building")
@RestController
public class BuildingController {

    private final VWorldService vWorldService;
    private final JusoApiService jusoApiService;

    @GetMapping("/use")
    public BuildingUseResponse getBuildingUse(BuildingRequest buildingRequest) throws UnsupportedEncodingException, JsonProcessingException {
        return vWorldService.getBuildingUse(buildingRequest);
    }

    @GetMapping("/info")
    public void getBuildingInfo(AddressRequest addressRequest) {
        jusoApiService.getBuildingInfo(addressRequest);
    }
}
