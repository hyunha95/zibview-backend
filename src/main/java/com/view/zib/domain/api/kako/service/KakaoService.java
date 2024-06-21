package com.view.zib.domain.api.kako.service;

import com.view.zib.domain.api.kako.domain.KakaoAddressResponse;

public interface KakaoService {

    KakaoAddressResponse searchAddress(String query);
}
