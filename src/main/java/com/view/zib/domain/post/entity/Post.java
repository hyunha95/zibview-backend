package com.view.zib.domain.post.entity;

import com.view.zib.domain.address.entity.Address;
import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.post.controller.request.PostRequest;
import com.view.zib.domain.user.entity.UserEntity;
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
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @OneToOne(fetch = FetchType.LAZY)
    private Address address;

    private String title;
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    @Builder.Default
    private List<Image> images = new ArrayList<>();

    public static Post from(UserEntity user, PostRequest.Save postRequest, List<Image> images, Address address) {
        Post post = Post.builder()
                .userEntity(user)
                .address(address)
                .images(images)
                .title(postRequest.getTitle())
                .description(postRequest.getDescription())
                .build();

        images.forEach(image -> image.addPost(post));

        return post;
    }

}
