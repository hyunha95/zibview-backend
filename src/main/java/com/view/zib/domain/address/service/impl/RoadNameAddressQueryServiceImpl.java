package com.view.zib.domain.address.service.impl;

import com.view.zib.domain.address.entity.RoadNameAddress;
import com.view.zib.domain.address.repository.RoadNameAddressRepository;
import com.view.zib.domain.address.service.RoadNameAddressQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoadNameAddressQueryServiceImpl implements RoadNameAddressQueryService {

    private final RoadNameAddressRepository roadNameAddressRepository;

    @Override
    public RoadNameAddress getById(String managementNo) {
        return roadNameAddressRepository.getById(managementNo);
    }
}
