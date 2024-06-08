package com.view.zib.domain.comment.entity;

import com.view.zib.domain.user.entity.User;
import com.view.zib.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class CommentLike extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Column(length = 1, columnDefinition = "tinyint(1)")
    private boolean like;

    public static CommentLike of(Comment comment, User currentUser) {
        return CommentLike.builder()
                .user(currentUser)
                .comment(comment)
                .like(true)
                .build();
    }

    public void unLike() {
        this.like = false;
    }

    public void like() {
        this.like = true;
    }
}
