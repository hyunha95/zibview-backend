package com.view.zib.domain.post.service;

import com.view.zib.domain.post.entity.SubPost;
import com.view.zib.domain.post.entity.SubPostLike;
import com.view.zib.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface SubPostLikeQueryService {
    Optional<SubPostLike> findBySubPostIdAndUserId(Long subPostId, Long currentUserId);

    void create(SubPost subPost, User currentUser);

    List<SubPostLike> findBySubPostIdInAndUserId(List<Long> subPostIds, Long userId);
}
