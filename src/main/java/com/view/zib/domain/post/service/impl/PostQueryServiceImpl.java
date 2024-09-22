package com.view.zib.domain.post.service.impl;

import com.view.zib.domain.post.entity.Post;
import com.view.zib.domain.post.repository.PostRepository;
import com.view.zib.domain.post.repository.dto.LatestPost;
import com.view.zib.domain.post.service.PostQueryService;
import com.view.zib.global.event.publisher.PostEventPublisher;
import com.view.zib.global.event.PostEventType;
import com.view.zib.global.exception.exceptions.ResourceNotFoundException;
import com.view.zib.global.utils.IpAddressUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class PostQueryServiceImpl implements PostQueryService {

    private final PostRepository postRepository;

    private final PostEventPublisher postEventPublisher;
    private final HttpServletRequest httpServletRequest;

    @Transactional
    @Override
    public Post getById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", id));
        postEventPublisher.publishEvent(post.getId(), IpAddressUtil.getClientIp(httpServletRequest), PostEventType.POST_VIEWED, LocalDateTime.now());
        return post;
    }

    @Override
    public Post getByIdForUpdate(Long postId) {
        return postRepository.findByIdForUpdate(postId).orElseThrow(() -> new ResourceNotFoundException("Post", postId));
    }

    @Override
    public Slice<LatestPost> getLatestPosts(Pageable pageable) {
        return postRepository.findAllLatestPosts(pageable);
    }
}
