package com.view.zib.domain.post.entity;

import com.view.zib.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.Assert;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "sub_post_like")
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

    public static SubPostLike of(SubPost subPost, User currentUser, boolean liked) {
        Assert.notNull(subPost, "SubPost must not be null");
        Assert.notNull(currentUser, "User must not be null");

        return SubPostLike.builder()
                .subPost(subPost)
                .user(currentUser)
                .liked(liked)
                .build();
    }

    public void like() {
        this.liked = true;
    }

    public void dislike() {
        this.liked = false;
    }
}
