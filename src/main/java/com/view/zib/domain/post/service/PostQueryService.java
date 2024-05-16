package com.view.zib.domain.post.service;

import com.view.zib.domain.post.controller.request.GetPostsRequest;

public interface PostQueryService {
    void getPostByClosestAddress(GetPostsRequest request);
}
