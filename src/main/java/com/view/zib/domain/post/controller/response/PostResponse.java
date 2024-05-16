package com.view.zib.domain.post.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class PostResponse {


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Save extends PostResponse {
        private Long postId;

        public static Save from(Long postId) {
            return new Save(postId);
        }
    }
}
