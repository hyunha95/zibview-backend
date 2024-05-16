package com.view.zib.domain.api.kako.domain;

import lombok.Builder;

/**
 * https://developers.kakao.com/docs/latest/ko/local/dev-guide#address-coord-request-query
 */
public record KakaoMapRequest(
        String query,        //	검색을 원하는 질의어
        String analyzeType, //
        int page,            //	Integer	결과 페이지 번호 // (최소: 1, 최대: 45, 기본값: 1)
        int size             //	Integer	한 페이지에 보여질 문서의 개수 // (최소: 1, 최대: 30, 기본값: 10)
) {

    public KakaoMapRequest(String query) {
        this(query, "", 1, 10);
    }
}
