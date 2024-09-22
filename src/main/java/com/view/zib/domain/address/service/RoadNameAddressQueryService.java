package com.view.zib.domain.address.service;

import com.view.zib.domain.address.entity.RoadNameAddress;

public interface RoadNameAddressQueryService {
    RoadNameAddress getById(String managementNo);
}
