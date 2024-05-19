package com.view.zib.domain.post.controller;

import com.view.zib.domain.post.controller.request.GetPostsRequest;
import com.view.zib.domain.post.controller.response.GetPostResponse;
import com.view.zib.domain.post.controller.response.GetPostsResponse;
import com.view.zib.domain.post.service.PostQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/posts")
@RequiredArgsConstructor
@RestController
public class PostQueryController {

    private final PostQueryService postQueryService;

    @GetMapping
    public List<GetPostsResponse> getPostByClosestAddress(GetPostsRequest request) {
        return postQueryService.getPostByClosestAddress(request);
    }

    @GetMapping("/{postId}")
    public GetPostResponse getPostDetails(@PathVariable("postId") Long postId) {
        return postQueryService.getPostDetails(postId);
    }
}
