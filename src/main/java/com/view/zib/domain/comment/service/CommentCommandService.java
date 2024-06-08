package com.view.zib.domain.comment.service;

import com.view.zib.domain.comment.controller.request.CreateCommentRequest;
import com.view.zib.domain.comment.controller.request.LikeCommentRequest;

public interface CommentCommandService {
    Long createComment(CreateCommentRequest request);

    void likeComment(LikeCommentRequest request);
}
