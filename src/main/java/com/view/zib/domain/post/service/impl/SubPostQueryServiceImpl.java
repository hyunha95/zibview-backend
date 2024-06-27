package com.view.zib.domain.post.service.impl;

import com.view.zib.domain.post.entity.SubPost;
import com.view.zib.domain.post.repository.SubPostRepository;
import com.view.zib.domain.post.service.SubPostQueryService;
import com.view.zib.global.exception.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SubPostQueryServiceImpl implements SubPostQueryService {

    private final SubPostRepository subPostRepository;

    @Override
    public SubPost getById(Long subPostId) {
        return this.findById(subPostId)
                .orElseThrow(() -> new ResourceNotFoundException("SubPost", subPostId));
    }

    public Optional<SubPost> findById(Long subPostId) {
        return subPostRepository.findById(subPostId);
    }

    @Override
    public SubPost getByIdForUpdate(Long subPostId) {
        return subPostRepository.findByIdForUpdate(subPostId)
                .orElseThrow(() -> new ResourceNotFoundException("SubPost", subPostId));
    }

    @Override
    public List<SubPost> findByPostIdAndDeletedFalseOrderByIdDesc(Long postId) {
        return subPostRepository.findByPostIdAndDeletedFalseOrderByIdDesc(postId);
    }
}
