package com.view.zib.domain.post.service;

import com.view.zib.domain.address.domain.Coordinate;
import com.view.zib.domain.post.controller.response.GetPostResponse;
import com.view.zib.domain.post.entity.Post;
import com.view.zib.domain.post.repository.dto.LatestResidentialPost;
import com.view.zib.domain.storage.service.StorageService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostQueryService {

    Post getById(Long postId);

    Slice<LatestResidentialPost> getLatestPosts(Pageable pageable);

    GetPostResponse getPostDetails(Long postId, StorageService storageService);

    Coordinate getCoordinateByPostId(Long postId);

}
