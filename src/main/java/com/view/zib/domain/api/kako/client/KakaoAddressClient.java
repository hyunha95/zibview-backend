package com.view.zib.domain.api.kako.client;


import com.view.zib.domain.api.kako.domain.KakaoAddressResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

/**
 * @see com.view.zib.global.config.HttpInterfaceConfig
 */
public interface KakaoAddressClient {

    /**
     * @param query         // 검색을 원하는 질의어
     * @param analyzeType   //
     * @param page          // Integer 결과 페이지 번호 (최소: 1, 최대: 45, 기본값: 1)
     * @param size          // Integer	한 페이지에 보여질 문서의 개수 // (최소: 1, 최대: 30, 기본값: 10)
     * @return
     */
    @GetExchange("/search/address.json")
    KakaoAddressResponse searchAddress(
            @RequestParam(name = "query") String query,
            @RequestParam(name = "analyze_type") String analyzeType,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size);

}
