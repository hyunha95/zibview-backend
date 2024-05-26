package com.view.zib.domain.address.domain;

import lombok.Builder;

public record Coordinate(
        double latitude,
        double longitude
) {

    @Builder
    public Coordinate {
    }
}
