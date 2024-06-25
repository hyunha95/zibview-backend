package com.view.zib.domain.address.service.impl;

import com.view.zib.domain.address.entity.RoadNameAddress;
import com.view.zib.domain.address.repository.RoadNameAddressRepository;
import com.view.zib.domain.address.service.RoadNameAddressCommandService;
import com.view.zib.domain.api.kako.domain.Coordinate;
import com.view.zib.domain.post.entity.Post;
import com.view.zib.global.exception.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RoadNameAddressCommandServiceImpl implements RoadNameAddressCommandService {

    private final RoadNameAddressRepository roadNameAddressRepository;

    @Transactional
    @Override
    public void updateCoordinate(Post post, Coordinate coordinate) {
        RoadNameAddress roadNameAddress = roadNameAddressRepository.findByPostId(post.getId())
                .orElseThrow(() -> new ResourceNotFoundException("RoadNameAddress.Post", post.getId()));

        roadNameAddress.updateCoordinate(coordinate);

    }
}
