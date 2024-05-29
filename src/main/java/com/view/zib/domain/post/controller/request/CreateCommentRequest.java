package com.view.zib.domain.post.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateCommentRequest(@NotNull @Positive Long parentCommentId, @NotBlank @Size(max = 300) String comment) {
}
