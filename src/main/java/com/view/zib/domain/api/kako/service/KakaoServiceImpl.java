package com.view.zib.domain.api.kako.service;

import com.view.zib.domain.api.kako.client.KakaoAddressClient;
import com.view.zib.domain.api.kako.domain.KakaoAddressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KakaoServiceImpl implements KakaoService {

    private final KakaoAddressClient kakaoAddressClient;

    @Override
    public KakaoAddressResponse searchAddress(String query) {
        return kakaoAddressClient.searchAddress(query, "", 1, 10);
    }
}
