package com.view.zib.domain.api.vworld.client;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

/**
 * https://www.vworld.kr/dev/v4dv_search2_s001.do#type_address
 * @see com.view.zib.global.config.HttpInterfaceConfig
 */
public interface AddressSearchClient {

    @GetExchange("/req/search")
    String searchAddress(
            @RequestParam String key, // 인증키
            @RequestParam String request,
            @RequestParam String query, // 검색 키워드
            @RequestParam String type, // PLACE : 장소, ADDRESS : 주소, DISTRICT : 행정구역, ROAD : 도로명
            @RequestParam String category
    );
}
