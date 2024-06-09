package com.view.zib.domain.comment.service;

import com.view.zib.domain.comment.controller.request.CreateCommentRequest;
import com.view.zib.domain.comment.controller.request.ToggleLikeRequest;
import com.view.zib.domain.comment.controller.response.ToggleLikeResponse;

public interface CommentCommandService {
    Long createComment(CreateCommentRequest request);

    ToggleLikeResponse toggleLike(ToggleLikeRequest request);
}
