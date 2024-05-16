package com.view.zib.domain.post.controller;

import com.view.zib.domain.post.controller.request.GetPostsRequest;
import com.view.zib.domain.post.service.PostQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@RestController
public class PostQueryController {

    private final PostQueryService postQueryService;

    @GetMapping
    public String getPostByClosestAddress(GetPostsRequest request) {
        postQueryService.getPostByClosestAddress(request);
        return "";
    }


}
