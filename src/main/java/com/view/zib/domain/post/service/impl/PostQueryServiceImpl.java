package com.view.zib.domain.post.service.impl;

import com.view.zib.domain.post.controller.request.GetPostsRequest;
import com.view.zib.domain.post.repository.PostRepository;
import com.view.zib.domain.post.service.PostQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostQueryServiceImpl implements PostQueryService {

    private final PostRepository postRepository;

    @Override
    public void getPostByClosestAddress(GetPostsRequest request) {

    }
}
