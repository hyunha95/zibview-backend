package com.view.zib.domain.address.facade;

import com.view.zib.domain.address.entity.Address;
import com.view.zib.domain.address.entity.AddressDetail;
import com.view.zib.domain.address.service.AddressDetailCommandService;
import com.view.zib.domain.address.service.AddressQueryService;
import com.view.zib.domain.client.vworld.dto.VWorldResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AddressDetailCommandFacade {

    private final AddressQueryService addressQueryService;

    private final AddressDetailCommandService addressDetailCommandService;

    public void create(String addressId, VWorldResponseDto vWorldResponseDto) {
        Address address = addressQueryService.getById(addressId);
        AddressDetail addressDetail = AddressDetail.of(address, vWorldResponseDto);
        addressDetailCommandService.create(addressDetail);
    }
}
