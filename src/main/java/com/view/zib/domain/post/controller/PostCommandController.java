package com.view.zib.domain.post.controller;

import com.view.zib.domain.post.controller.request.CreateCommentRequest;
import com.view.zib.domain.post.controller.request.PostRequest;
import com.view.zib.domain.post.controller.response.CreateCommentResponse;
import com.view.zib.domain.post.controller.response.PostResponse;
import com.view.zib.domain.post.facade.PostFacade;
import com.view.zib.domain.post.service.PostCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/posts")
@RequiredArgsConstructor
@RestController
public class PostCommandController {

    private final PostFacade postFacade;
    private final PostCommandService postCommandService;

    @PostMapping
    public ResponseEntity<PostResponse.Save> save(@RequestBody @Valid PostRequest.Save request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postFacade.save(request));
    }

    @PostMapping("/comments")
    public ResponseEntity<CreateCommentResponse> createComment(@RequestBody @Valid CreateCommentRequest request) {
        Long newCommentId = postCommandService.createComment(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateCommentResponse(newCommentId));
    }
}
