package com.view.zib.domain.post.entity;

import com.view.zib.domain.address.entity.Address;
import com.view.zib.domain.address.entity.RoadNameAddress;
import com.view.zib.domain.image.entity.Image;
import com.view.zib.domain.post.enums.RentType;
import com.view.zib.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

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
    @JoinColumn(name = "image_id")
    private Image mostRecentImage;

    @OneToOne
    @JoinColumn(name = "road_name_address_id")
    private RoadNameAddress roadNameAddress;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "post")
    private Address address;

    private double rating;

    private int likeCount;
    private int commentCount;

    public static Post from(Address address) {
        Post post = Post.builder()
                .address(address)
                .build();

        address.addEntity(post);

        return post;
    }

    /**
     * RentType 별로 마지막 SubPost를 반환한다.
     * @return
     */
    public Map<RentType, SubPost> getLatestSubPosts() {
        return subPosts.stream()
                .collect(groupingBy(
                        subPost -> subPost.getContractInfo().getRentType(),
                        collectingAndThen(
                                toList(),
                                list -> list.stream().sorted(comparing(SubPost::getCreatedAt)).toList().getLast()
                        )
                ));
    }

    public List<SubPost> getSubPostsNotDeletedOrderByIdDesc() {
        return subPosts.stream()
                .filter(subPost -> !subPost.isDeleted())
                .sorted(comparing(SubPost::getId).reversed())
                .toList();
    }
}
