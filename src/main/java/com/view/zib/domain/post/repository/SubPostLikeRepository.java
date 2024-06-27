package com.view.zib.domain.post.repository;

import com.view.zib.domain.post.entity.SubPostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubPostLikeRepository extends JpaRepository<SubPostLike, Long> {

    @Query("select spl from SubPostLike spl where spl.subPost.id = :subPostId and spl.user.id = :currentUserId")
    Optional<SubPostLike> findBySubPostIdAndUserId(Long subPostId, Long currentUserId);

    @Query("select spl from SubPostLike spl where spl.subPost.id in :subPostIds and spl.user.id = :id")
    List<SubPostLike> findBySubPostIdInAndUserId(List<Long> subPostIds, Long id);
}
