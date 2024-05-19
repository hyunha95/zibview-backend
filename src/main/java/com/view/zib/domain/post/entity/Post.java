package com.view.zib.domain.post.entity;

import com.view.zib.domain.address.entity.Address;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL)
    private List<SubPost> subPosts = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private Address address;

    public static Post from(Address address) {
        return Post.builder()
                .address(address)
                .build();
    }
}
