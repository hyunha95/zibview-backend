package com.view.zib.domain.post.controller.response;

import com.view.zib.domain.comment.entity.Comment;

import java.time.LocalDateTime;

public record CommentDTO(
        Long userId,
        String userName,
        String profileImageUrl,
        String comment,
        LocalDateTime createdAt
) {
    // Comment Entity를 CommentDto로 변환
    public static CommentDTO from(Comment comment) {
        return new CommentDTO(
                comment.getUser().getId(),
                comment.getUser().getName(),
                comment.getUser().getPictureUrl(),
                comment.getComment(),
                comment.getCreatedAt()
        );
    }
}
