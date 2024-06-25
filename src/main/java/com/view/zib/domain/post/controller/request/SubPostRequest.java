package com.view.zib.domain.post.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public class SubPostRequest {
    @NotNull @Positive
    private Long postId;

    @NotBlank @Size(max = 100)
    private String title;

    @NotBlank @Size(max = 1000)
    private String description;
    private List<String> imageUuids;

    public SubPostRequest(Long postId, String title, String description, List<String> imageUuids) {
        this.postId = postId;
        this.title = title;
        this.description = description;
        this.imageUuids = imageUuids;
    }

    @Getter
    public static class Save extends SubPostRequest {
        public Save(Long postId, String title, String description, List<String> imageUuids) {
            super(postId, title, description, imageUuids);
        }
    }
}
