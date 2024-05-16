package com.view.zib.domain.api.building.service;

import com.view.zib.domain.api.building.controller.request.AddressRequest;

public interface JusoApiService {

    void getBuildingInfo(AddressRequest addressRequest);
}
