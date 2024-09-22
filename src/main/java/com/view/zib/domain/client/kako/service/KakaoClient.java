package com.view.zib.domain.client.kako.service;

import com.view.zib.domain.client.kako.domain.Coordinate;

public interface KakaoClient {
    Coordinate searchCoordinateByRoadAddress(String roadAddress);
    Coordinate searchCoordinateByRoadAddress(String roadAddress, String managementNo);
}
