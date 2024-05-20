package com.view.zib.domain.post.enums;

import lombok.Getter;

@Getter
public enum RentType {

    MONTHLY("월세")
    , ANNUAL("전세");

    private String description;

    RentType(String description) {
        this.description = description;
    }
}
