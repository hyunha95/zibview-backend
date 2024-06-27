package com.view.zib.domain.post.repository;

import com.view.zib.domain.post.entity.SubPost;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubPostRepository extends JpaRepository<SubPost, Long>{

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from SubPost s where s.id = :subPostId")
    Optional<SubPost> findByIdForUpdate(Long subPostId);

    @Query("select s from SubPost s where s.post.id = :postId and s.deleted = false order by s.id desc")
    List<SubPost> findByPostIdAndDeletedFalseOrderByIdDesc(Long postId);
}
