package com.view.zib.domain.address.service.impl;

import com.view.zib.domain.address.entity.Address;
import com.view.zib.domain.address.repository.AddressRepository;
import com.view.zib.domain.address.service.AddressCommandService;
import com.view.zib.domain.api.kako.domain.Coordinate;
import com.view.zib.domain.post.entity.Post;
import com.view.zib.global.exception.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AddressCommandServiceImpl implements AddressCommandService {

    private final AddressRepository addressRepository;

    @Transactional
    @Override
    public void updateCoordinate(Post post, Coordinate coordinate) {
        Address address = addressRepository.findByPostId(post.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Address.Post", post.getId()));

        address.updateCoordinate(coordinate);
    }
}
