package com.view.zib.domain.client.juso.domain;

import lombok.Builder;

public record DetailPurpose (String detailPurposeCode, String detailPurposeCodeName) {

    @Builder
    public DetailPurpose {
    }
}
