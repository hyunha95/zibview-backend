package com.view.zib.domain.post.service.impl;

import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.post.controller.request.SubPostRequest;
import com.view.zib.domain.post.entity.Post;
import com.view.zib.domain.post.entity.SubPost;
import com.view.zib.domain.post.repository.SubPostRepository;
import com.view.zib.domain.post.service.SubPostCommandService;
import com.view.zib.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SubPostCommandServiceImpl implements SubPostCommandService {

    private final SubPostRepository subPostRepository;

    @Transactional
    @Override
    public Long create(SubPostRequest.Save request, Post post, List<Image> images, User currentUser) {
        SubPost subPost = SubPost.of(request, post, images, currentUser);
        subPostRepository.save(subPost);
        return subPost.getId();
    }
}
