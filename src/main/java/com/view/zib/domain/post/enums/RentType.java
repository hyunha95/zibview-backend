package com.view.zib.domain.post.enums;

import lombok.Getter;

@Getter
public enum RentType {
    DEPOSIT("전세"),
    MONTHLY("월세"),
    MIXED("반전세");

    private String description;

    RentType(String description) {
        this.description = description;
    }
}
