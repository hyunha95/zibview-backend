package com.view.zib.domain.post.service;

import com.view.zib.domain.post.entity.Post;
import com.view.zib.domain.post.repository.dto.LatestResidentialPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostQueryService {

    Post getById(Long postId);

    Slice<LatestResidentialPost> getLatestPosts(Pageable pageable);
}
