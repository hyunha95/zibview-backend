package com.view.zib.domain.client.juso.domain;

import lombok.Builder;

import java.util.Objects;

public record BuildingUseApiRequest(
        String pnu,
        String mainPrposCode, // 주요용도코드
        String detailPrposCode, // 세부용도코드
        String format, // 응답결과 형식(xml 또는 json)
        int numOfRows, // 검색건수 (최대 1000)
        int pageNo, // 페이지 번호
        String key, // 발급받은 인증키
        String domain // API KEY를 발급받을때 입력했던 URL
) {

    @Builder
    public BuildingUseApiRequest {
        Objects.requireNonNull(pnu);
        Objects.requireNonNull(key);
    }

    public BuildingUseApiRequest(String pnu, String key) {
        this(pnu, "", "", "", 0, 0, key, "");
    }
}
