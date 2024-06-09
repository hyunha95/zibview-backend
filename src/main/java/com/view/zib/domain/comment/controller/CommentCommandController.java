package com.view.zib.domain.comment.controller;

import com.view.zib.domain.comment.controller.request.CreateCommentRequest;
import com.view.zib.domain.comment.controller.request.ToggleLikeRequest;
import com.view.zib.domain.comment.controller.response.CreateCommentResponse;
import com.view.zib.domain.comment.controller.response.ToggleLikeResponse;
import com.view.zib.domain.comment.service.CommentCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/comments")
@RequiredArgsConstructor
@RestController
public class CommentCommandController {

    private final CommentCommandService commentCommandService;

    @PostMapping
    public ResponseEntity<CreateCommentResponse> createComment(@RequestBody @Valid CreateCommentRequest request) {
        Long newCommentId = commentCommandService.createComment(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateCommentResponse(newCommentId));
    }

    @PostMapping("/likes/toggle")
    public ResponseEntity<ToggleLikeResponse> toggleLike(@RequestBody @Valid ToggleLikeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).
                body(commentCommandService.toggleLike(request));
    }
}
