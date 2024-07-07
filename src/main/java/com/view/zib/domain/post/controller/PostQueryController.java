package com.view.zib.domain.post.controller;

import com.view.zib.domain.elasticsearch.document.PostSearchAsYouType;
import com.view.zib.domain.post.controller.response.GetPostResponse;
import com.view.zib.domain.post.controller.response.GetPostsResponse;
import com.view.zib.domain.post.facade.PostQueryFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@RestController
public class PostQueryController {

    private final PostQueryFacade postQueryFacade;

    @GetMapping
    public Slice<GetPostsResponse> getLatestPosts(Pageable pageable) {
        return postQueryFacade.getLatestPosts(pageable);
    }

    @GetMapping("/{postId}")
    public GetPostResponse getPostDetails(@PathVariable("postId") Long postId) {
        return postQueryFacade.getPostDetails(postId);
    }

    @GetMapping("/search-as-you-type")
    public List<PostSearchAsYouType> searchAsYouType(String query) {
        return postQueryFacade.searchAsYouTypeAddressAndBuildingName(query);
    }
}
