package com.view.zib.domain.comment.entity;

import com.view.zib.domain.post.entity.SubPost;
import com.view.zib.domain.user.entity.User;
import com.view.zib.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_post_id")
    private SubPost subPost;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @Builder.Default
    @OneToMany(mappedBy = "parent")
    private List<Comment> children = new ArrayList<>();

    private String comment;

    @Column(length = 1, columnDefinition = "tinyint(1)")
    private boolean deleted;
    private Long likeCount;

    public static Comment of(String comment, User user, SubPost subPost, Comment parentComment) {
        return Comment.builder()
                .user(user)
                .subPost(subPost)
                .parent(parentComment)
                .comment(comment)
                .deleted(false)
                .build();
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        this.likeCount--;
    }
}
