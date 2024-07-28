package com.view.zib.domain.post.service;

import com.view.zib.domain.post.entity.Post;
import com.view.zib.domain.post.repository.dto.LatestPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostQueryService {

    Post getById(Long postId);
    Post getByIdForUpdate(Long postId);

    Slice<LatestPost> getLatestPosts(Pageable pageable);
}
