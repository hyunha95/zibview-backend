package com.view.zib.domain.comment.repository;

import com.view.zib.domain.comment.entity.CommentLike;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT cl FROM CommentLike cl WHERE cl.user.id = :userId AND cl.comment.id = :commentId")
    Optional<CommentLike> findByUserIdAndCommentIdForUpdate(@Param("userId") Long userId, @Param("commentId") Long commentId);
}
