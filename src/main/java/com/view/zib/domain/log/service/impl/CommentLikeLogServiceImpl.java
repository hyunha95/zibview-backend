package com.view.zib.domain.log.service.impl;

import com.view.zib.domain.comment.CommentAction;
import com.view.zib.domain.comment.entity.CommentLike;
import com.view.zib.domain.log.entity.CommentLikeLog;
import com.view.zib.domain.log.repository.CommentLikeLogRepository;
import com.view.zib.domain.log.service.CommentLikeLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentLikeLogServiceImpl implements CommentLikeLogService {

    private final CommentLikeLogRepository commentLikeLogRepository;

    @Override
    public void log(CommentLike commentLike, CommentAction commentAction) {
        commentLikeLogRepository.save(CommentLikeLog.of(commentLike, commentAction));
    }
}
