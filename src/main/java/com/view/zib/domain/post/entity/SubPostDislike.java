package com.view.zib.domain.post.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class SubPostDislike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_sub_post_dislike_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_post_id")
    private SubPost subPost;
}
