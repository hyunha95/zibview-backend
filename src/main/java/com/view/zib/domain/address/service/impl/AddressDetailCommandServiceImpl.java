package com.view.zib.domain.address.service.impl;

import com.view.zib.domain.address.entity.AddressDetail;
import com.view.zib.domain.address.repository.AddressDetailRepository;
import com.view.zib.domain.address.service.AddressDetailCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class AddressDetailCommandServiceImpl implements AddressDetailCommandService {

    private final AddressDetailRepository addressDetailRepository;

    @Override
    public String create(AddressDetail addressDetail) {
        AddressDetail newAddressDetail = addressDetailRepository.save(addressDetail);
        return newAddressDetail.getId();
    }
}
