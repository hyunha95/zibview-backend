package com.view.zib.domain.address.service.impl;

import com.view.zib.domain.address.entity.RoadNameAddress;
import com.view.zib.domain.address.repository.RoadNameAddressRepository;
import com.view.zib.domain.address.service.RoadNameAddressUpdateService;
import com.view.zib.domain.client.kako.domain.Coordinate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class RoadNameAddressUpdateServiceImpl implements RoadNameAddressUpdateService {

    private final RoadNameAddressRepository roadNameAddressRepository;

    @Override
    public void updateCoordinate(String managementNo, Coordinate coordinate) {
        RoadNameAddress roadNameAddress = roadNameAddressRepository.getById(managementNo);
//        roadNameAddress.updateCoordinate(coordinate.latitude(), coordinate.longitude());
    }
}
