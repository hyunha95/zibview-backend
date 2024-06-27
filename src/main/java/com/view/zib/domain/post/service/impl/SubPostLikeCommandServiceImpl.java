package com.view.zib.domain.post.service.impl;

import com.view.zib.domain.post.entity.SubPostLike;
import com.view.zib.domain.post.repository.SubPostLikeRepository;
import com.view.zib.domain.post.service.SubPostLikeCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SubPostLikeCommandServiceImpl implements SubPostLikeCommandService {

    private final SubPostLikeRepository subPostLikeRepository;

    @Transactional
    @Override
    public void delete(SubPostLike subPostLike) {
        subPostLikeRepository.delete(subPostLike);
    }
}
