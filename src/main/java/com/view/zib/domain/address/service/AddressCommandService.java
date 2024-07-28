package com.view.zib.domain.address.service;

import com.view.zib.domain.api.kako.domain.Coordinate;

public interface AddressCommandService {
    void updateCoordinate(Long postId, Coordinate coordinate);
}
