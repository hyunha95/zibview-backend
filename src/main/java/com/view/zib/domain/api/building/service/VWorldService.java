package com.view.zib.domain.api.building.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.view.zib.domain.api.building.controller.request.BuildingRequest;
import com.view.zib.domain.api.building.controller.response.BuildingUseResponse;

import java.io.UnsupportedEncodingException;

public interface VWorldService {

    BuildingUseResponse getBuildingUse(BuildingRequest buildingUseRequest) throws UnsupportedEncodingException, JsonProcessingException;
}
