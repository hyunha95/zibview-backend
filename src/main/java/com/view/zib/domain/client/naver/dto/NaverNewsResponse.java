package com.view.zib.domain.client.naver.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.view.zib.global.common.CustomLocalDateTimeDeserializer;

import java.time.LocalDateTime;
import java.util.List;

public record NaverNewsResponse(
        @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
        LocalDateTime lastBuildDate,
        Long total,
        Long start,
        Long display,
        List<Item> items
) {
    public record Item(
            String title,
            String originallink,
            String link,
            String description,
            @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
            LocalDateTime pubDate
    ) {}
}
