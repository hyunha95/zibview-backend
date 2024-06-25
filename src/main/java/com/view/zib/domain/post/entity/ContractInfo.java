package com.view.zib.domain.post.entity;

import com.view.zib.domain.post.controller.request.PostRequest;
import com.view.zib.domain.post.enums.RentType;
import com.view.zib.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class ContractInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_info_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_post_id")
    private SubPost subPost;

    @Enumerated(EnumType.STRING)
    private RentType rentType;

    public static ContractInfo of(PostRequest.ContractInfo contractInfo, Post post) {
        return com.view.zib.domain.post.entity.ContractInfo.builder()
                .post(post)

                .build();
    }

    public void addEntity(SubPost subPost) {
        this.subPost = subPost;
    }
}
