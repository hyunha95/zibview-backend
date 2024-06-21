package com.view.zib.domain.post.repository.custom;

import com.view.zib.domain.post.repository.dto.LatestResidentialPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostCustomRepository {

    Slice<LatestResidentialPost> findAllLatestResidentialPosts(Pageable pageable);

}