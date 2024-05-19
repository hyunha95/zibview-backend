package com.view.zib.domain.post.controller.response;

import com.view.zib.domain.post.entity.Post;

public record GetPostsResponse(
        Long postId,
        String address,
        String buildingName
//        String imageUrl
) {

    public GetPostsResponse(Post post) {
        this(post.getId(), post.getAddress().getAddress(), post.getAddress().getBuildingName());
    }
}
