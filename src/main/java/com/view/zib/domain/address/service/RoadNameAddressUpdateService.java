package com.view.zib.domain.address.service;

import com.view.zib.domain.client.kako.domain.Coordinate;

public interface RoadNameAddressUpdateService {
    void updateCoordinate(String managementNo, Coordinate coordinate);
}
