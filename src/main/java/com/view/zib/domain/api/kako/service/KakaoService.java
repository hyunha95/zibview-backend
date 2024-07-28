package com.view.zib.domain.api.kako.service;

import com.view.zib.domain.api.kako.domain.Coordinate;

public interface KakaoService {
    Coordinate searchCoordinateByRoadAddress(String roadAddress);
}
