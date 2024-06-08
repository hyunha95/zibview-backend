package com.view.zib.domain.post.service.impl;

import com.view.zib.domain.post.entity.SubPost;
import com.view.zib.domain.post.repository.SubPostRepository;
import com.view.zib.domain.post.service.SubPostService;
import com.view.zib.global.exception.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SubPostServiceImpl implements SubPostService {

    private final SubPostRepository subPostRepository;

    @Override
    public SubPost getById(Long subPostId) {
        return this.findById(subPostId)
                .orElseThrow(() -> new ResourceNotFoundException("SubPost", subPostId));
    }

    public Optional<SubPost> findById(Long subPostId) {
        return subPostRepository.findById(subPostId);
    }
}
