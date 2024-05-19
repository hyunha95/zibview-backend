package com.view.zib.domain.post.service;

import com.view.zib.domain.post.controller.request.GetPostsRequest;
import com.view.zib.domain.post.controller.response.GetPostResponse;
import com.view.zib.domain.post.controller.response.GetPostsResponse;

import java.util.List;

public interface PostQueryService {
    List<GetPostsResponse> getPostByClosestAddress(GetPostsRequest request);

    GetPostResponse getPostDetails(Long postId);
}
