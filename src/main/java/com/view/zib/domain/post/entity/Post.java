package com.view.zib.domain.post.entity;

import com.view.zib.domain.address.entity.Address;
import com.view.zib.domain.building.enums.BuildingType;
import com.view.zib.domain.image.entity.Image;
import com.view.zib.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
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

    @OneToOne(fetch = FetchType.LAZY)
    private Address address;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    private double rating;

    private int likeCount;
    private int commentCount;

    private BuildingType buildingType;

    public static Post from(Address address) {
        return Post.builder()
                .address(address)
                .build();
    }

    /**
     * 오름차순 정렬 후 마지막 SubPost를 반환한다.
     * @return
     */
    public SubPost getLastestSubPost() {
        subPosts.sort(Comparator.comparing(SubPost::getCreatedAt));
        return subPosts.getLast();
    }

    public void updateBuildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
    }

    public void updateImage(Image lastRepresentativeImage) {
        this.image = lastRepresentativeImage;
    }
}
