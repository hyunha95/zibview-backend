package com.view.zib.domain.post.service;

import com.view.zib.domain.post.entity.SubPost;

import java.util.List;

public interface SubPostQueryService {

    SubPost getById(Long subPostId);

    SubPost getByIdForUpdate(Long subPostId);

    List<SubPost> findByPostIdAndDeletedFalseOrderByIdDesc(Long postId);
}
