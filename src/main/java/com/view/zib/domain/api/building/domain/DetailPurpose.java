package com.view.zib.domain.api.building.domain;

import lombok.Builder;

public record DetailPurpose (String detailPurposeCode, String detailPurposeCodeName) {

    @Builder
    public DetailPurpose {
    }
}
