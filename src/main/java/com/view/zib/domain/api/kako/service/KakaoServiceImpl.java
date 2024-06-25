package com.view.zib.domain.api.kako.service;

import com.view.zib.domain.api.kako.client.KakaoAddressClient;
import com.view.zib.domain.api.kako.domain.KakaoAddressResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoServiceImpl implements KakaoService {

    private final KakaoAddressClient kakaoAddressClient;

    @Override
    public KakaoAddressResponse searchAddress(String query) {
        log.info("kakao Address API Call with [query : {}]", query);
        return kakaoAddressClient.searchAddress(query, "", 1, 10);
    }
}
