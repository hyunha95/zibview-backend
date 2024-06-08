package com.view.zib.domain.log.entity;

import com.view.zib.global.jpa.UnmodifiableEntity;
import jakarta.persistence.*;

@Table(name = "log_comment_like")
@Entity
public class CommentLikeLog extends UnmodifiableEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_like_id")
    private Long id;
    private String email;
}
