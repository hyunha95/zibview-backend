package com.view.zib.domain.address.service;

import com.view.zib.domain.address.domain.Coordinate;

public interface AddressQueryService {

    Coordinate getCoordinateByPostId(Long postId);
}
