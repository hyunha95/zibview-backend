package com.view.zib.domain.api.kako.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Meta (
        @JsonProperty("total_count")
        int totalCount,

        @JsonProperty("pageable_count")
        int pageableCount,

        @JsonProperty("is_end")
        boolean isEnd
) {
}
