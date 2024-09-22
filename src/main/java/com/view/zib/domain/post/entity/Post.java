package com.view.zib.domain.post.entity;

import com.view.zib.domain.address.entity.Address;
import com.view.zib.domain.address.entity.RoadNameAddress;
import com.view.zib.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL)
    private List<SubPost> subPosts = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "management_no")
    private RoadNameAddress roadNameAddress;

    private double rating;
    private long likeCount;
    private long commentCount;
    private long viewCount;

    public static Post from(Address address) {
        return Post.builder()
//                .address(address)
                .build();
    }

    public void updateTime() {
        setUpdatedAt(LocalDateTime.now());
    }

    public void increaseViewCount() {
        viewCount++;
    }
}
