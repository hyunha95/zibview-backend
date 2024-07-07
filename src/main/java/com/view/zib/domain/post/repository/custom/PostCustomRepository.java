package com.view.zib.domain.post.repository.custom;

import com.view.zib.domain.post.repository.dto.LatestPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostCustomRepository {

    Slice<LatestPost> findAllLatestPosts(Pageable pageable);

}