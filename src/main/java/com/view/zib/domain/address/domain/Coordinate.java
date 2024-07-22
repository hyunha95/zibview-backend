package com.view.zib.domain.address.domain;

import lombok.Builder;

import java.math.BigDecimal;

public record Coordinate(
        BigDecimal latitude,
        BigDecimal longitude
) {

    @Builder
    public Coordinate {
    }
}
