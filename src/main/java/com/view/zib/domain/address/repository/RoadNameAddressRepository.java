package com.view.zib.domain.address.repository;

import com.view.zib.domain.address.entity.RoadNameAddress;
import com.view.zib.domain.address.repository.jpa.RoadNameAddressJpaRepository;
import com.view.zib.global.exception.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RoadNameAddressRepository {

    private final RoadNameAddressJpaRepository roadNameAddressJpaRepository;

    public RoadNameAddress getById(String managementNo) {
        return roadNameAddressJpaRepository.findById(managementNo)
                .orElseThrow(() -> new ResourceNotFoundException("RoadNameAddress", managementNo));
    }
}
