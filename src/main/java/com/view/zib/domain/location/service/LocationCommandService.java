package com.view.zib.domain.location.service;

import com.view.zib.domain.location.controller.request.SaveLocationRequest;

public interface LocationCommandService {
    void saveLocation(SaveLocationRequest request);
}
