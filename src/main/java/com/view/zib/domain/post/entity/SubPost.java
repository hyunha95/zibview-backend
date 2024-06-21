package com.view.zib.domain.post.entity;

import com.view.zib.domain.comment.entity.Comment;
import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.post.controller.request.PostRequest;
import com.view.zib.domain.user.entity.User;
import com.view.zib.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;

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
    private ContractInfo contractInfo;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subPost")
    private List<Comment> comments = new ArrayList<>();

    private String title;
    private String description;
    private double rating;

    @Column(length = 1, columnDefinition = "tinyint(1)")
    private boolean deleted;
    private Long commentCount;

    private LocalDate contractStartDate;
    private LocalDate contractEndDate;

    public static SubPost of(User user, PostRequest.Save request, List<Image> images, Post post, ContractInfo contractInfo) {
        SubPost subPost = SubPost.builder()
                .post(post)
                .user(user)
                .images(images)
                .contractInfo(contractInfo)
                .title(request.getTitle())
                .description(request.getDescription())
                .contractStartDate(request.getContractInfo().getContractStartDate())
                .contractEndDate(request.getContractInfo().getContractEndDate())
                .build();

        images.forEach(image -> image.addEntity(subPost));
        post.getSubPosts().add(subPost);
        contractInfo.addEntity(subPost);
        return subPost;
    }

    public Optional<Comment> getLatestComment() {
        return comments.stream()
                .filter(comment -> !comment.isDeleted())
                .sorted(comparing(Comment::getCreatedAt).reversed())
                .findAny();
    }
}
