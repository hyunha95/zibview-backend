package com.view.zib.domain.post.entity;

import com.view.zib.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class SubPostLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_sub_post_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_post_id")
    private SubPost subPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private boolean liked;

    public static SubPostLike of(SubPost subPost, User currentUser) {
        return SubPostLike.builder()
                .subPost(subPost)
                .user(currentUser)
                .build();
    }
}
