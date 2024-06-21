package com.view.zib.domain.log.entity;

import com.view.zib.domain.comment.CommentAction;
import com.view.zib.domain.comment.entity.CommentLike;
import com.view.zib.global.jpa.UnmodifiableEntity;
import jakarta.persistence.*;

import java.util.Objects;

@Table(name = "log_comment_like")
@Entity
public class CommentLikeLog extends UnmodifiableEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_like_id")
    private Long id;
    private String email;
    private Long commentId;
    private boolean liked;

    public static CommentLikeLog of(CommentLike commentLike, CommentAction commentAction) {
        CommentLikeLog commentLikeLog = new CommentLikeLog();
        commentLikeLog.email = commentLike.getUser().getEmail();
        commentLikeLog.commentId = commentLike.getComment().getId();
        commentLikeLog.liked = Objects.equals(commentAction, CommentAction.CREATE);
        return commentLikeLog;
    }
}
