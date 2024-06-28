package com.view.zib.domain.post.service.impl;

import com.view.zib.domain.post.entity.SubPost;
import com.view.zib.domain.post.entity.SubPostLike;
import com.view.zib.domain.post.repository.SubPostLikeRepository;
import com.view.zib.domain.post.service.SubPostLikeQueryService;
import com.view.zib.domain.user.entity.User;
import com.view.zib.global.exception.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SubPostLikeQueryServiceImpl implements SubPostLikeQueryService {

    private final SubPostLikeRepository subPostLikeRepository;

    @Override
    public SubPostLike getBySubPostIdAndUserId(Long subPostId, Long userId) {
        return this.findBySubPostIdAndUserId(subPostId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("SubPostLike", subPostId, userId, "해당 게시물에 좋아요를 누른 기록이 없습니다."));
    }

    @Override
    public Optional<SubPostLike> findBySubPostIdAndUserId(Long subPostId, Long currentUserId) {
        return subPostLikeRepository.findBySubPostIdAndUserId(subPostId, currentUserId);
    }

    @Override
    public List<SubPostLike> findBySubPostIdInAndUserId(List<Long> subPostIds, Long userId) {
        return subPostLikeRepository.findBySubPostIdInAndUserId(subPostIds, userId);
    }

    @Override
    public void create(SubPost subPost, User currentUser) {
        SubPostLike subPostLike = SubPostLike.of(subPost, currentUser);
        subPostLikeRepository.save(subPostLike);
    }
}
