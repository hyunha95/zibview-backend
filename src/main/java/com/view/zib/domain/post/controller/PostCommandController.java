package com.view.zib.domain.post.controller;

import com.view.zib.domain.post.controller.request.PostRequest;
import com.view.zib.domain.post.controller.request.SubPostRequest;
import com.view.zib.domain.post.facade.PostCommandFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/posts")
@RequiredArgsConstructor
@RestController
public class PostCommandController {

    private final PostCommandFacade postCommandFacade;

    @PostMapping
    public ResponseEntity<PostId> create(@RequestBody @Valid PostRequest.Save request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new PostId(postCommandFacade.create(request)));
    }

    @PostMapping("/sub-post")
    public ResponseEntity<SubPostId> saveSubPost(@RequestBody @Valid SubPostRequest.Save request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SubPostId(postCommandFacade.createSubPost(request)));
    }

    @PostMapping("/sub-post/{subPostId}/toggle-like")
    public ResponseEntity<Void> toggleLikeSubPost(@PathVariable Long subPostId) {
        postCommandFacade.toggleLikeSubPost(subPostId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sub-post/{subPostId}/dislike")
    public ResponseEntity<Void> dislikeSubPost(@PathVariable Long subPostId) {
//        postCommandFacade.dislikeSubPost(subPostId);
        return ResponseEntity.ok().build();
    }

    public record PostId (Long postId) {}
    public record SubPostId (Long subPostId) {}
}
