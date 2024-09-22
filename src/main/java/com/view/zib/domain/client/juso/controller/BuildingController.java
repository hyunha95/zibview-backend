package com.view.zib.domain.client.juso.controller;

import com.view.zib.domain.client.juso.service.JusoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/building")
@RestController
public class BuildingController {

    private final JusoClient jusoClient;


//    @GetMapping("/info")
//    public void getBuildingInfo(AddressRequest addressRequest) {
//        jusoClient.getBuildingInfo(addressRequest);
//    }
}
