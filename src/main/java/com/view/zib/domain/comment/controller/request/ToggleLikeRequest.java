package com.view.zib.domain.comment.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ToggleLikeRequest(
        @NotNull @Positive Long commentId
) {
}
