package com.view.zib.domain.log.service;

import com.view.zib.domain.comment.CommentAction;
import com.view.zib.domain.comment.entity.CommentLike;

public interface CommentLikeLogService {
    void log(CommentLike commentLike, CommentAction commentAction);
}
