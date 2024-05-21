package com.view.zib.domain.post.entity;

import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.post.controller.request.PostRequest;
import com.view.zib.domain.user.entity.User;
import com.view.zib.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class SubPost extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subPost")
    private List<Image> images = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "subPost")
    private Rent rent;

    private String title;
    private String description;

    private double rating;

    @Column(length = 1, columnDefinition = "tinyint(1)")
    private boolean deleted;

    public static SubPost of(User user, PostRequest.Save postRequest, List<Image> images, Post post) {
        SubPost subPost = SubPost.builder()
                .post(post)
                .user(user)
                .images(images)
                .title(postRequest.getTitle())
                .description(postRequest.getDescription())
                .build();

        images.forEach(image -> image.addEntity(subPost));
        post.getSubPosts().add(subPost);

        return subPost;
    }
}
