package com.view.zib.domain.api.kako.service;

import com.view.zib.domain.api.kako.domain.Document;
import com.view.zib.domain.api.kako.domain.KakaoMapRequest;

public interface KakaoMapService {

    Document searchAddress(KakaoMapRequest request);
}
