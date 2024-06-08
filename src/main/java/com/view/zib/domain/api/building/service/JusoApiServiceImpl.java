package com.view.zib.domain.api.building.service;

import com.view.zib.domain.api.building.controller.request.AddressRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class JusoApiServiceImpl implements JusoApiService {

    private final String jusoKey;

    public JusoApiServiceImpl(@Value("${api.juso.key}") String jusoKey) {
        this.jusoKey = jusoKey;
    }

    @Override
    public void getBuildingInfo(AddressRequest addressRequest) {
        searchAddressesFromJusoAPI(addressRequest);
    }


    public void searchAddressesFromJusoAPI(AddressRequest addressRequest) {
        RestClient restClient = RestClient.create("https://business.juso.go.kr/addrlink/addrLinkApi.do");
        String body = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("confmKey", jusoKey)
                        .queryParam("currentPage", "1")
                        .queryParam("countPerPage", "10")
                        .queryParam("keyword", addressRequest.address())
                        .queryParam("resultType", "json")
                        .build())
                .retrieve()
                .body(String.class);

        System.out.println(body);
    }
}
