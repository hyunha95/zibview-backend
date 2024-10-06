package com.view.zib.domain.address.repository.dto;

import lombok.Builder;

@Builder
public record JibunMultipleConditionDTO(
        String legalDongCode,
        int jibunMain,
        int jibunSub
) {
}
