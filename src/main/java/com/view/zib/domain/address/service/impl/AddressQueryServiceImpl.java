package com.view.zib.domain.address.service.impl;

import com.view.zib.domain.address.domain.Coordinate;
import com.view.zib.domain.address.entity.Address;
import com.view.zib.domain.address.repository.AddressRepository;
import com.view.zib.domain.address.service.AddressQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AddressQueryServiceImpl implements AddressQueryService {

    private final AddressRepository addressRepository;

    public Coordinate getCoordinateByPostId(Long postId) {
        Address address = addressRepository.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));

        return Coordinate.builder()
                .latitude(address.getLatitude())
                .longitude(address.getLongitude())
                .build();
    }
}
