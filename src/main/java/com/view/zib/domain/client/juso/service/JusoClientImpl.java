package com.view.zib.domain.client.juso.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class JusoClientImpl implements JusoClient {

    private final String jusoKey;

    public JusoClientImpl(@Value("${api.juso.key}") String jusoKey) {
        this.jusoKey = jusoKey;
    }


    public void searchAddress(String address) {
        RestClient restClient = RestClient.create("https://business.juso.go.kr/addrlink/addrLinkApi.do");
        String body = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("confmKey", jusoKey)
                        .queryParam("currentPage", "1")
                        .queryParam("countPerPage", "10")
                        .queryParam("keyword", address)
                        .queryParam("resultType", "json")
                        .build())
                .retrieve()
                .body(String.class);
        System.out.println(body);
    }
}
