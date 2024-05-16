package com.view.zib.domain.post.controller;

import com.view.zib.domain.post.controller.request.PostRequest;
import com.view.zib.domain.post.controller.response.PostResponse;
import com.view.zib.domain.post.service.PostCommandService;
import com.view.zib.domain.post.service.PostFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@RestController
public class PostCommandController {

    private final PostFacade postFacade;

    @PostMapping
    public PostResponse.Save save(@RequestBody PostRequest.Save request) {
        return postFacade.save(request);
    }
}
